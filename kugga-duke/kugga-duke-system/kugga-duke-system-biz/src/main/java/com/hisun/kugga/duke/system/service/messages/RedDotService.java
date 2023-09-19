package com.hisun.kugga.duke.system.service.messages;


import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.system.controller.app.message.vo.RedDotRespVO;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author: zhou_xiong
 */
public interface RedDotService {
    /**
     * 监听
     *
     * @return
     */
    DeferredResult<RedDotRespVO> watch();

    /**
     * 发布
     */
    void publish(RedDotReqDTO redDotReqDTO);
}
