package com.hisun.kugga.duke.system.controller.app.shorturl;

import com.hisun.kugga.duke.system.controller.app.shorturl.vo.ShortCodeRespVO;
import com.hisun.kugga.duke.system.service.ShortUrlService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author zuocheng
 */
@Api(tags = "公会邀请短链")
@RestController
@RequestMapping("/g")
@Validated
@Slf4j
public class LeagueShortUrlController {
    @Resource
    private ShortUrlService shortUrlService;

    @GetMapping("/{shortCode}")
    @ApiOperation("获取短链信息")
    public CommonResult<ShortCodeRespVO> get(@PathVariable String shortCode) {
        return success(shortUrlService.queryShortUrlByCode(shortCode));
    }
}
