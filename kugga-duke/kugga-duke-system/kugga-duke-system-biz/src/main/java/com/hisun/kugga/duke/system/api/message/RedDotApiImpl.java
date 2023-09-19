package com.hisun.kugga.duke.system.api.message;

import com.hisun.kugga.duke.system.api.message.dto.RedDotReqDTO;
import com.hisun.kugga.duke.system.service.messages.RedDotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: zhou_xiong
 */
@Service
public class RedDotApiImpl implements RedDotApi {
    @Resource
    private RedDotService redDotService;

    @Override
    public void publish(RedDotReqDTO redDotReqDTO) {
        redDotService.publish(redDotReqDTO);
    }
}
