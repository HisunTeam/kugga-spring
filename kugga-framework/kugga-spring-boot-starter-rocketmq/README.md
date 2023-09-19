# Apache RocketMQ with Spring Boot

by baeldungDataSpring BootMessaging
If you have a few years of experience in the Java ecosystem, and you’d like to share that with the community, have a look at our Contribution Guidelines.

## 1. Introduction
   In this tutorial, we’ll create a message producer and consumer using Spring Boot and Apache RocketMQ, an open-source distributed messaging and streaming data platform.

## 2. Dependencies
   For Maven projects, we need to add the RocketMQ Spring Boot Starter dependency:

```
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>
```

## 3. Producing Messages
For our example, we’ll create a basic message producer that will send events whenever the user adds or removes an item from the shopping cart.

First, let's set up our server location and group name in our application.properties:
```
rocketmq.name-server=127.0.0.1:9876
rocketmq.producer.group=cart-producer-group
```
Note that if we had more than one name server, we could list them like host:port;host:port.

Now, to keep it simple, we’ll create a CommandLineRunner application and generate a few events during application startup:

```java
@SpringBootApplication
public class CartEventProducer implements CommandLineRunner {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static void main(String[] args) {
        SpringApplication.run(CartEventProducer.class, args);
    }

    public void run(String... args) throws Exception {
        rocketMQTemplate.convertAndSend("cart-item-add-topic", new CartItemEvent("bike", 1));
        rocketMQTemplate.convertAndSend("cart-item-add-topic", new CartItemEvent("computer", 2));
        rocketMQTemplate.convertAndSend("cart-item-removed-topic", new CartItemEvent("bike", 1));
    }
}
```
The CartItemEvent consists of just two properties – the id of the item and a quantity:

```java
class CartItemEvent {
private String itemId;
private int quantity;

    // constructor, getters and setters
}
```
In the above example, we use the convertAndSend() method, a generic method defined by the AbstractMessageSendingTemplate abstract class, to send our cart events. It takes two parameters: A destination, which in our case is a topic name, and a message payload.

## 4. Message Consumer
   Consuming RocketMQ messages is as simple as creating a Spring component annotated with @RocketMQMessageListener and implementing the RocketMQListener interface:

```java
@SpringBootApplication
public class CartEventConsumer {

    public static void main(String[] args) {
        SpringApplication.run(CartEventConsumer.class, args);
    }

    @Service
    @RocketMQMessageListener(
      topic = "cart-item-add-topic",
      consumerGroup = "cart-consumer_cart-item-add-topic"
    )
    public class CardItemAddConsumer implements RocketMQListener<CartItemEvent> {
        public void onMessage(CartItemEvent addItemEvent) {
            log.info("Adding item: {}", addItemEvent);
            // additional logic
        }
    }

    @Service
    @RocketMQMessageListener(
      topic = "cart-item-removed-topic",
      consumerGroup = "cart-consumer_cart-item-removed-topic"
    )
    public class CardItemRemoveConsumer implements RocketMQListener<CartItemEvent> {
        public void onMessage(CartItemEvent removeItemEvent) {
            log.info("Removing item: {}", removeItemEvent);
            // additional logic
        }
    }
}
```
We need to create a separate component for every message topic we are listening for. In each of these listeners, we define the name of the topic and consumer group name through the @RocketMQMessageListener annotation.

## 5. Synchronous and Asynchronous Transmission
   In the previous examples, we used the convertAndSend method to send our messages. We have some other options, though.

We could, for example, call syncSend which is different from convertAndSend because it returns SendResult object.

It can be used, for example, to verify if our message was sent successfully or get its id:

```java
public void run(String... args) throws Exception {
    SendResult addBikeResult = rocketMQTemplate.syncSend("cart-item-add-topic",
    new CartItemEvent("bike", 1));
    SendResult addComputerResult = rocketMQTemplate.syncSend("cart-item-add-topic",
    new CartItemEvent("computer", 2));
    SendResult removeBikeResult = rocketMQTemplate.syncSend("cart-item-removed-topic",
    new CartItemEvent("bike", 1));
}
```
Like convertAndSend, this method is returned only when the sending procedure completes.

We should use synchronous transmission in cases requiring high reliability, such as important notification messages or SMS notification.

On the other hand, we may instead want to send the message asynchronously and be notified when the sending completes.

We can do this with asyncSend, which takes a SendCallback as a parameter and returns immediately:

```java
rocketMQTemplate.asyncSend("cart-item-add-topic", new CartItemEvent("bike", 1), new SendCallback() {
    @Override
    public void onSuccess(SendResult sendResult) {
    log.error("Successfully sent cart item");
    }

    @Override
    public void onException(Throwable throwable) {
        log.error("Exception during cart item sending", throwable);
    }
});
```
We use asynchronous transmission in cases requiring high throughput.

Lastly, for scenarios where we have very high throughput requirements, we can use sendOneWay instead of asyncSend. sendOneWay is different from asyncSend in that it doesn't guarantee the message gets sent.

One-way transmission can also be used for ordinary reliability cases, such as collecting logs.

## 6. Sending Messages in Transaction
   RocketMQ provides us with the ability to send messages within a transaction. We can do it by using the sendInTransaction() method:

```java
MessageBuilder.withPayload(new CartItemEvent("bike", 1)).build();
rocketMQTemplate.sendMessageInTransaction("test-transaction", "topic-name", msg, null);
```
Also, we must implement a RocketMQLocalTransactionListener interface:

```java
@RocketMQTransactionListener(txProducerGroup="test-transaction")
class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
    // ... local transaction process, return ROLLBACK, COMMIT or UNKNOWN
    return RocketMQLocalTransactionState.UNKNOWN;
    }

      @Override
      public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
          // ... check transaction status and return ROLLBACK, COMMIT or UNKNOWN
          return RocketMQLocalTransactionState.COMMIT;
      }
}
```
In sendMessageInTransaction(), the first parameter is the transaction name. It must be the same as the @RocketMQTransactionListener‘s member field txProducerGroup.

## 7. Message Producer Configuration
   We can also configure aspects of the message producer itself:

+ rocketmq.producer.send-message-timeout: The message send timeout in milliseconds – the default value is 3000
+ rocketmq.producer.compress-message-body-threshold: Threshold above which, RocketMQ will compress messages – the default value is 1024.
+ rocketmq.producer.max-message-size: The maximum message size in bytes – the default value is 4096.
+ rocketmq.producer.retry-times-when-send-async-failed: The maximum number of retries to perform internally in asynchronous mode before sending failure – the default value is 2.
+ rocketmq.producer.retry-next-server: Indicates whether to retry another broker on sending failure internally – the default value is false.
+ rocketmq.producer.retry-times-when-send-failed: The maximum number of retries to perform internally in asynchronous mode before sending failure – the default value is 2.

## 8. Conclusion
   In this article, we’ve learned how to send and consume messages using Apache RocketMQ and Spring Boot. As always all source code is available on GitHub.

Get started with Spring 5 and Spring Boot 2, through the Learn Spring course:

