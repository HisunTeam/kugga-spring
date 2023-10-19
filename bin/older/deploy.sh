#!/bin/bash
set -e

DATE=$(date +%Y%m%d%H%M)
# Basic Location
BASE_PATH=/work/projects/kugga-server
# The location of the compiled jar file. During deployment, Jenkins will upload the jar package to this directory.
SOURCE_PATH=$BASE_PATH/build
# Service name. It's also agreed that the name of the jar package deployed is the same.
SERVER_NAME=kugga-server
# Environment
PROFILES_ACTIVE=development
# Health check URL
HEALTH_CHECK_URL=http://127.0.0.1:18080/actuator/health/

# Path for heapError
HEAP_ERROR_PATH=$BASE_PATH/heapError
# JVM Parameters
JAVA_OPS="-Xms512m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$HEAP_ERROR_PATH"

# SkyWalking Agent configuration
#export SW_AGENT_NAME=$SERVER_NAME
#export SW_AGENT_COLLECTOR_BACKEND_SERVICES=192.168.0.84:11800
#export SW_GRPC_LOG_SERVER_HOST=192.168.0.84
#export SW_AGENT_TRACE_IGNORE_PATH="Redisson/PING,/actuator/**,/admin/**"
#export JAVA_AGENT=-javaagent:/work/skywalking/apache-skywalking-apm-bin/agent/skywalking-agent.jar

# Backup
function backup() {
    # If it doesn't exist, no need to back up
    if [ ! -f "$BASE_PATH/$SERVER_NAME.jar" ]; then
        echo "[backup] $BASE_PATH/$SERVER_NAME.jar does not exist, skipping backup"
    # If it exists, back it up to the backup directory, using time as the suffix
    else
        echo "[backup] Starting backup $SERVER_NAME ..."
        cp $BASE_PATH/$SERVER_NAME.jar $BASE_PATH/backup/$SERVER_NAME-$DATE.jar
        echo "[backup] Backup $SERVER_NAME completed"
    fi
}

# Move the latest built code to the project environment
function transfer() {
    echo "[transfer] Starting to transfer $SERVER_NAME.jar"

    # Delete the original jar package
    if [ ! -f "$BASE_PATH/$SERVER_NAME.jar" ]; then
        echo "[transfer] $BASE_PATH/$SERVER_NAME.jar does not exist, skipping deletion"
    else
        echo "[transfer] Removal of $BASE_PATH/$SERVER_NAME.jar completed"
        rm $BASE_PATH/$SERVER_NAME.jar
    fi

    # Copy new jar package
    echo "[transfer] Fetching $SERVER_NAME.jar from $SOURCE_PATH and moving to $BASE_PATH ...."
    cp $SOURCE_PATH/$SERVER_NAME.jar $BASE_PATH

    echo "[transfer] Transfer of $SERVER_NAME.jar completed"
}

# Stop: Gracefully shut down the service that was previously started
function stop() {
    echo "[stop] Starting to stop $BASE_PATH/$SERVER_NAME"
    PID=$(ps -ef | grep $BASE_PATH/$SERVER_NAME | grep -v "grep" | awk '{print $2}')
    # If Java service is running, then shut it down
    if [ -n "$PID" ]; then
        # Normal shutdown
        echo "[stop] $BASE_PATH/$SERVER_NAME is running, start to kill [$PID]"
        kill -15 $PID
        # Wait up to 120 seconds until shutdown is complete.
        for ((i = 0; i < 120; i++))
            do
                sleep 1
                PID=$(ps -ef | grep $BASE_PATH/$SERVER_NAME | grep -v "grep" | awk '{print $2}')
                if [ -n "$PID" ]; then
                    echo -e ".\c"
                else
                    echo '[stop] Stopping $BASE_PATH/$SERVER_NAME successful'
                    break
                fi
            done

        # If normal shutdown fails, then forcefully kill -9 to shut down
        if [ -n "$PID" ]; then
            echo "[stop] $BASE_PATH/$SERVER_NAME failed, force kill -9 $PID"
            kill -9 $PID
        fi
    # If Java service is not running, no need to shut down
    else
        echo "[stop] $BASE_PATH/$SERVER_NAME is not running, no need to stop"
    fi
}

# Start: Start the backend project
function start() {
    # Before starting, print start parameters
    echo "[start] Starting $BASE_PATH/$SERVER_NAME"
    echo "[start] JAVA_OPS: $JAVA_OPS"
    echo "[start] JAVA_AGENT: $JAVA_AGENT"
    echo "[start] PROFILES: $PROFILES_ACTIVE"

    # Start
    BUILD_ID=dontKillMe nohup java -server $JAVA_OPS $JAVA_AGENT -jar $BASE_PATH/$SERVER_NAME.jar --spring.profiles.active=$PROFILES_ACTIVE &
    echo "[start] Starting $BASE_PATH/$SERVER_NAME completed"
}

# Health check: Automatically determine whether the backend project has started normally
function healthCheck() {
    # If health check is configured, then do health check
    if [ -n "$HEALTH_CHECK_URL" ]; then
        # Health check up to 120 seconds, until health check passes
        echo "[healthCheck] Starting health check through $HEALTH_CHECK_URL";
        for ((i = 0; i < 120; i++))
            do
                # Request health check URL, only get status code.
                result=`curl -I -m 10 -o /dev/null -s -w %{http_code} $HEALTH_CHECK_URL || echo "000"`
                # If status code is 200, then health check is passed
                if [ "$result" == "200" ]; then
                    echo "[healthCheck] Health check passed";
                    break
                # If status code is not 200, then it's not passed. sleep 1 second, then retry
                else
                    echo -e ".\c"
                    sleep 1
                fi
            done

        # If health check is not passed, then exit shell script abnormally, do not continue deployment.
        if [ ! "$result" == "200" ]; then
            echo "[healthCheck] Health check failed, deployment might have failed. Check the logs to determine if it started successfully";
            tail -n 10 nohup.out
            exit 1;
        # If health check is passed, print last 10 lines of logs, maybe the deployer wants to see the logs.
        else
            tail -n 10 nohup.out
        fi
    # If health check is not configured, then sleep 120 seconds, manually check logs to see if deployment is successful.
    else
        echo "[healthCheck] HEALTH_CHECK_URL not set, starting sleep for 120 seconds";
        sleep 120
        echo "[healthCheck] Finished sleeping for 120 seconds, check logs to determine if it started successfully";
        tail -n 50 nohup.out
    fi
}

# Deploy
function deploy() {
    cd $BASE_PATH
    # Backup original jar
    backup
    # Stop Java service
    stop
    # Deploy new jar
    transfer
    # Start Java service
    start
    # Health check
    healthCheck
}

deploy
