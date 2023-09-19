package com.hisun.kugga.duke.system.controller.app.shorturl;

import com.hisun.kugga.duke.system.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zuocheng
 */
@Api(tags = "短链接")
@RestController
@RequestMapping("/s")
@Validated
@Slf4j
public class ShortUrlController {
    @Resource
    private ShortUrlService shortUrlService;

    @GetMapping("/{shortCode}")
    @ApiOperation("短链重定向")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) {
        String url = shortUrlService.getLongUrl(shortCode);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            log.info("链接[{}]重定向错误", url, e);
        }
    }
}
