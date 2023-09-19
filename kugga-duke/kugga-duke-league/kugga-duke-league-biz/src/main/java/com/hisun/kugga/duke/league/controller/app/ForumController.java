package com.hisun.kugga.duke.league.controller.app;

import com.hisun.kugga.duke.league.service.ForumService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

@Api(tags = "论坛")
@RestController
@RequestMapping("/duke/forum")
@Validated
@Slf4j
public class ForumController {

    @Resource
    private ForumService forumService;

    @GetMapping("/index")
    @ApiOperation("进入论坛，返回KuggaMax论坛url，前端只需重定向到这个url")
    @ApiImplicitParam(name = "leagueId", value = "公会id", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<String> forumIndex(@RequestParam("leagueId") Long leagueId) {
        String url = forumService.forumIndex(leagueId);
        return success(url);
    }

}
