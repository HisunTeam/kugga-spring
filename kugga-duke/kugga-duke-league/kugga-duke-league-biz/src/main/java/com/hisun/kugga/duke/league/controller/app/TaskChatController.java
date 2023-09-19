package com.hisun.kugga.duke.league.controller.app;

import com.hisun.kugga.duke.league.service.TaskChatService;
import com.hisun.kugga.duke.league.vo.task.TaskChatResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskChatVO;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.*;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-08-04 17:12
 */
@Api(tags = "任务-聊天")
@RestController
@RequestMapping("/league/taskChat")
@Validated
@Slf4j
public class TaskChatController {

    @Resource
    TaskChatService taskChatService;
    @Resource
    WalletApi walletApi;

    @PostMapping("/judgeChatPay")
    @ApiOperation("点击聊天 判断是否付费")
    public CommonResult<TaskChatResultVO> judgeChatPay(@Valid @RequestBody TaskChatVO vo) {
        return taskChatService.judgeChatPay(vo);
    }

    @GetMapping("/get")
    @ApiOperation("测试0001")
    public CommonResult get(@RequestParam("id") Long id) {
//        41569504375956561920
        CreateAccountRspBody account = walletApi.createAccount(new CreateAccountReqBody());
        return success(account);
    }

    @GetMapping("/test03")
    public CommonResult test03(String str) {
//        41569504375956561920
        PreChargeReqBody req = new PreChargeReqBody()
                .setAccount(str)
                .setAmount(10000)
                .setReturnUrl("www.baidu.com");
        PreChargeRspBody preChargeRspBody = walletApi.preCharge(req);
        return success(preChargeRspBody);
    }

    @GetMapping("/test04")
    public CommonResult test04(String str) {
        AccountDetailRspBody body = walletApi.accountDetail(new AccountDetailReqBody().setAccount(str));
        return success(body);
    }
}
