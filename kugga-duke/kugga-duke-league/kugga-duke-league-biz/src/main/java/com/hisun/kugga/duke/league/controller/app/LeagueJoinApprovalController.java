package com.hisun.kugga.duke.league.controller.app;

import com.hisun.kugga.duke.league.service.LeagueJoinApprovalService;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalPageReqVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalRespVO;
import com.hisun.kugga.duke.league.vo.joinLeague.LeagueJoinApprovalUpdateReqVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "A7.加入公会审批")
@RestController
@RequestMapping("/league-join/approval")
@Validated
public class LeagueJoinApprovalController {

    @Resource
    private LeagueJoinApprovalService leagueJoinApprovalService;

    @PutMapping("/update")
    @ApiOperation("更新审批")
    public CommonResult<Boolean> updateLeagueJoinApproval(@Valid @RequestBody LeagueJoinApprovalUpdateReqVO updateReqVO) {
        leagueJoinApprovalService.updateLeagueJoinApproval(updateReqVO);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获得加入公会审批分页")
    public CommonResult<PageResult<LeagueJoinApprovalRespVO>> getLeagueJoinApprovalPage(@Valid LeagueJoinApprovalPageReqVO pageVO) {
        PageResult<LeagueJoinApprovalRespVO> pageResult = leagueJoinApprovalService.getLeagueJoinApprovalPage(pageVO);
        return success(pageResult);
    }

    @GetMapping("/getApprovalCount")
    @ApiOperation("获取公会待审批记录")
    public CommonResult<Long> getApprovalCount(@Valid LeagueJoinApprovalPageReqVO pageVO) {
        Long count = leagueJoinApprovalService.getApprovalCount();
        return success(count);
    }

}
