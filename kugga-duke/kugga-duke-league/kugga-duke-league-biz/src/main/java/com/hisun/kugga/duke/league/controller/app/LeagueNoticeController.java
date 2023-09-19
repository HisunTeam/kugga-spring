package com.hisun.kugga.duke.league.controller.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.service.LeagueNoticeService;
import com.hisun.kugga.duke.league.vo.notice.LeagueNoticeVO;
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

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:12
 */
@Api(tags = "公会 - 公告栏")
@RestController
@RequestMapping("/league/notice")
@Validated
@Slf4j
public class LeagueNoticeController {

    @Resource
    LeagueNoticeService leagueNoticeService;

    @GetMapping("/getAll")
    @ApiOperation("点击详情 根据公会ID查询公会公告栏详情")
    @ApiImplicitParam(name = "id", value = "公会id", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Page<LeagueNoticeVO>> getAll(@RequestParam("id") Long id) {
        return success(leagueNoticeService.getAll(id));
    }

    @GetMapping("/page")
    @ApiOperation("分页查询公会公告栏")
    public CommonResult<Page<LeagueNoticeVO>> page(@RequestParam("id") Long id) {
        return success(leagueNoticeService.getPage(id));
    }
}
