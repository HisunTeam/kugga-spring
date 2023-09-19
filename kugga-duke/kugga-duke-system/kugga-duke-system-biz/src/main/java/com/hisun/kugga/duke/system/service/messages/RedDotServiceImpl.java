package com.hisun.kugga.duke.system.service.messages;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.hisun.kugga.duke.chat.api.ChatApi;
import com.hisun.kugga.duke.system.api.message.SendMessageApi;
import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.system.controller.app.message.vo.RedDotRespVO;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.util.Collection;

import static com.hisun.kugga.framework.web.config.KuggaWebAutoConfiguration.TIME_OUT;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Service
public class RedDotServiceImpl implements RedDotService {
    @Resource
    private SendMessageApi sendMessageApi;
    @Resource
    private ChatApi chatApi;
    /**
     * 存放监听userId的长轮询集合
     */
    public static Multimap<Long, DeferredResult<RedDotRespVO>> watchRequests = Multimaps.synchronizedMultimap(HashMultimap.create());

    @Override
    public DeferredResult<RedDotRespVO> watch() {
        // 延迟对象设置超时时间
        DeferredResult<RedDotRespVO> deferredResult = new DeferredResult<>(TIME_OUT);
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        // 异步请求完成时移除 key，防止内存溢出
        deferredResult.onCompletion(() ->
                watchRequests.remove(loginUserId, deferredResult)
        );
        deferredResult.onTimeout(() -> {
                    watchRequests.remove(loginUserId, deferredResult);
                    Boolean chatRedDot = chatApi.messageRetDot(loginUserId);
                    Boolean messageRedDot = sendMessageApi.unreadFlag(loginUserId);
                    RedDotRespVO redDotRespVO = new RedDotRespVO()
                            .setChatRedDot(chatRedDot)
                            .setMessageRedDot(messageRedDot);
                    deferredResult.setResult(redDotRespVO);
                }
        );
        watchRequests.put(loginUserId, deferredResult);
        return deferredResult;
    }

    @Async
    @Override
    public void publish(RedDotReqDTO redDotReqDTO) {
        if (ObjectUtil.isNull(redDotReqDTO.getUserId())) {
            return;
        }
        try {
            if (watchRequests.containsKey(redDotReqDTO.getUserId())) {
                Collection<DeferredResult<RedDotRespVO>> deferredResults = watchRequests.get(redDotReqDTO.getUserId());
                if (ObjectUtil.isEmpty(deferredResults)) {
                    return;
                }
                for (DeferredResult<RedDotRespVO> deferredResult : deferredResults) {
                    RedDotRespVO redDotRespVO = new RedDotRespVO();
                    BeanUtils.copyProperties(redDotReqDTO, redDotRespVO);
                    deferredResult.setResult(redDotRespVO);
                }
            }
        } catch (Exception ignored) {
            // 有异常忽略，不影响正常业务流程
            log.error("RedDot publish error:{}", ignored);
        }
    }
}
