package com.hisun.kugga.module.system.mq.consumer.permission;

import com.hisun.kugga.framework.mq.core.pubsub.AbstractChannelMessageListener;
import com.hisun.kugga.module.system.mq.message.permission.RoleRefreshMessage;
import com.hisun.kugga.module.system.service.permission.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link RoleRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class RoleRefreshConsumer extends AbstractChannelMessageListener<RoleRefreshMessage> {

    @Resource
    private RoleService roleService;

    @Override
    public void onMessage(RoleRefreshMessage message) {
        log.info("[onMessage][收到 Role 刷新消息]");
        roleService.initLocalCache();
    }

}
