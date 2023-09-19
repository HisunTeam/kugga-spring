package com.hisun.kugga.module.system.mq.producer.permission;

import com.hisun.kugga.framework.mq.core.RedisMQTemplate;
import com.hisun.kugga.module.system.mq.message.permission.RoleRefreshMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Role 角色相关消息的 Producer
 *
 * @author 芋道源码
 */
@Component
public class RoleProducer {

    @Resource
    private RedisMQTemplate redisMQTemplate;

    /**
     * 发送 {@link RoleRefreshMessage} 消息
     */
    public void sendRoleRefreshMessage() {
        RoleRefreshMessage message = new RoleRefreshMessage();
        redisMQTemplate.send(message);
    }

}
