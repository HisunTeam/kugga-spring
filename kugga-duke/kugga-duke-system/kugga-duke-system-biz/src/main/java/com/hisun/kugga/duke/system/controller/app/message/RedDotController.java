package com.hisun.kugga.duke.system.controller.app.message;

import com.hisun.kugga.duke.system.controller.app.message.vo.RedDotRespVO;
import com.hisun.kugga.duke.system.service.messages.RedDotService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;

/**
 * @author: zhou_xiong
 */
@Api(tags = "polling - 未读消息红点长轮询")
@RestController
@RequestMapping("/polling")
@Validated
public class RedDotController {
    @Resource
    private RedDotService redDotService;

    @GetMapping("/watch")
    @ApiOperation("获得消息未读小红点")
    public DeferredResult<RedDotRespVO> watch() {
        DeferredResult<RedDotRespVO> deferredResult = redDotService.watch();
        return deferredResult;
    }
}
