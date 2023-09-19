package com.hisun.kugga.duke.league.controller.app;

import cn.hutool.core.date.DateUtil;
import com.hisun.kugga.duke.league.service.LeagueJoinService;
import com.hisun.kugga.duke.league.vo.joinLeague.*;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;

import static com.hisun.kugga.duke.common.IdentifierConstants.yyyy_MM_dd;
import static com.hisun.kugga.duke.common.IdentifierConstants.yyyy_MM_dd_hh_mm_ss;
import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @Author: Lin
 * @Date: 2022-09-13 15:12
 */
@Api(tags = "A7.公会加入")
@RestController
@RequestMapping("/league/join")
@Validated
@Slf4j
public class LeagueJoinController {

    @Resource
    private LeagueJoinService leagueJoinService;

    @PostMapping("/joinLeagueInit")
    @ApiOperation("加入公会初始化下单")
    public CommonResult<LeagueJoinInitRespVO> joinLeagueInit(@Valid @RequestBody LeagueJoinInitReqVO vo) {
        /*
        测试流程
        1、找一个公会，它开启了 允许加入，且设置了金额
        2、输入公会id、钱、理由调用 joinLeagueInit，返回订单号  这里进行业务下单 和预下单
        3、找一个接口根据 输入原始密码 获取加密和公钥
        4、拿着 密文和公钥、订单号 调用 joinLeaguePay支付，这里会验证支付密码和实际支付，此时加入公会订单支付状态修改、审批记录插入
        5、获取审批记录 然后修改审批
         */
        LeagueJoinInitRespVO respVO = leagueJoinService.joinLeagueInit(vo);
        return success(respVO);
    }

    @PostMapping("/joinLeaguePay")
    @ApiOperation("加入公会支付")
    public CommonResult<Boolean> joinLeaguePay(@Valid @RequestBody LeagueJoinPayReqVO vo) {
        leagueJoinService.joinLeaguePay(vo);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("我加入公会的申请记录")
    public CommonResult<PageResult<LeagueJoinApprovalRespVO>> getLeagueJoinMyApprovalPage(@Valid LeagueJoinApprovalPageReqVO pageVO) {
        PageResult<LeagueJoinApprovalRespVO> pageResult = leagueJoinService.getLeagueJoinPage(pageVO);
        return success(pageResult);
    }


    @PostMapping("/dateTest")
    @ApiOperation("dateTest")
    public CommonResult<String> dateTest(@Valid @RequestBody LeagueJoinInitReqVO vo) {

        leagueJoinService.lockTest(vo);
        /*String date = vo.getJoinReason() + " 01:01:00";
        LocalDateTime localDateTime = DateUtil.parseLocalDateTime(date, yyyy_MM_dd_hh_mm_ss);
        LocalDateTime localDateTime2 = localDateTime.plusMonths(1L);
        DateUtil.format(localDateTime2, yyyy_MM_dd)
        */

        return success();
    }

}
