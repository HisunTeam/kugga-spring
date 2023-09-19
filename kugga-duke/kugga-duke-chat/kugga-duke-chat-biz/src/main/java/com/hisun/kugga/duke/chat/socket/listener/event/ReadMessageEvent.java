package com.hisun.kugga.duke.chat.socket.listener.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hisun.kugga.duke.chat.socket.constant.SocketEvent;
import com.hisun.kugga.duke.chat.socket.listener.event.entity.ReadMessage;
import com.hisun.kugga.duke.chat.socket.schedule.MessageSessionUpdateSchedule;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.socketio.handler.AbstractEventListenerHandler;

import javax.annotation.Resource;
import java.util.Optional;

import static com.hisun.kugga.duke.chat.enums.ErrorCodeConstantsChat.InputIsNull;

public class ReadMessageEvent extends AbstractEventListenerHandler<ReadMessage> {

    @Resource
    private MessageSessionUpdateSchedule messageSessionUpdateSchedule;

    @OnEvent(SocketEvent.READ_MESSAGE)
    public void onData(SocketIOClient client, ReadMessage readMessage) {
        logger.info("read_message:{}", readMessage);
        Optional.ofNullable(readMessage.getRoomId()).orElseThrow(() -> new ServiceException(InputIsNull, "roomId"));
        Optional.ofNullable(readMessage.getRecordId()).orElseThrow(() -> new ServiceException(InputIsNull, "recordId"));
        messageSessionUpdateSchedule.readMessage(readMessage.getRoomId(), readMessage.getRecordId());
    }
}
