package com.hisun.kugga.duke.system.api.message;

import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;

/**
 * @author: zhou_xiong
 */
public interface RedDotApi {
    /**
     * 发布红点
     *
     * @param redDotReqDTO
     */
    void publish(RedDotReqDTO redDotReqDTO);
}
