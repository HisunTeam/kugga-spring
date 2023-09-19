package com.hisun.kugga.duke.system.service.messages.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.system.dal.dataobject.MessagesDO;
import com.hisun.kugga.duke.system.dal.mysql.MessagesMapper;
import com.hisun.kugga.duke.system.service.messages.RedDotService;
import com.hisun.kugga.duke.system.service.messages.bo.MessageBo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 同步处理消费信息 db
 * @author： Lin
 * @Date 2022/7/30 15:08
 */
public class SyncMessageClient extends AbstractMessageClient {

    @Resource
    private MessagesMapper messagesMapper;
    @Resource
    private RedDotService redDotService;

    @Override
    protected void doSendMessage(List<MessageBo> messageBos) {
        if (ObjectUtil.isEmpty(messageBos)) {
            return;
        }
        List<MessagesDO> insertDos = new ArrayList<>();
        messageBos.forEach(i -> {
            MessagesDO messagesDO = MessagesDO.builder().build();
            BeanUtil.copyProperties(i, messagesDO);
            insertDos.add(messagesDO);
        });
        messagesMapper.insertBatch(insertDos);
        insertDos.forEach(message ->
                redDotService.publish(new RedDotReqDTO()
                        .setUserId(message.getReceiverId())
                        .setMessageRedDot(true))
        );
    }
}
