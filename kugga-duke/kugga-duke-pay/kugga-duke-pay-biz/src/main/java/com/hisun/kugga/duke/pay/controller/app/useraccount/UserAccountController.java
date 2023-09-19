package com.hisun.kugga.duke.pay.controller.app.useraccount;

import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.*;
import com.hisun.kugga.duke.pay.service.useraccount.UserAccountService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "用户账户")
@RestController
@RequestMapping("/duke/user-account")
@Validated
public class UserAccountController {

    @Resource
    private UserAccountService userAccountService;
    @Resource
    private UserAccountApi userAccountApi;

    @PostMapping("/get-account")
    @ApiOperation("获得当前用户账户余额")
    public CommonResult<BigDecimal> getAccount() {
        return success(userAccountService.getBalance(SecurityFrameworkUtils.getLoginUser().getAccountId()));
    }

    @PostMapping("/fee")
    @ApiOperation("获取交易手续费")
    public CommonResult<BigDecimal> tradeFee(@Valid @RequestBody TradeFeeReqVO tradeFeeReqVO) {
        return success(userAccountService.tradeFee(tradeFeeReqVO));
    }

    @GetMapping("/pwd-flag")
    @ApiOperation("当前登录用户是否设置支付密码【true: 已设置，false：未设置】")
    public CommonResult<Boolean> getPwdFlag() {
        return success(userAccountService.getPwdFlag(SecurityFrameworkUtils.getLoginUserId()));
    }

    @PostMapping("/email-code")
    @ApiOperation("设置/修改/重置支付密码发送邮箱验证码")
    public CommonResult sendEmailCode(@Valid @RequestBody EmailCodeReqVO emailCodeReqVO) {
        userAccountService.sendEmailCode(emailCodeReqVO.getEmailType());
        return success();
    }

    @PostMapping("/set-pwd")
    @ApiOperation("设置支付密码")
    public CommonResult setPassword(@Valid @RequestBody SetPayPasswordReqVO setPayPasswordReqVO) {
        userAccountService.setPassword(setPayPasswordReqVO, SecurityFrameworkUtils.getLoginUserId());
        return success();
    }

    @PostMapping("/update-pwd")
    @ApiOperation("修改支付密码")
    public CommonResult updatePassword(@Valid @RequestBody UpdatePayPasswordReqVO updatePayPasswordReqVO) {
        userAccountService.updatePassword(updatePayPasswordReqVO);
        return success();
    }

    @PostMapping("/reset-pwd")
    @ApiOperation("重置支付密码")
    public CommonResult resetPassword(@Valid @RequestBody ResetPayPasswordReqVO resetPayPasswordReqVO) {
        userAccountService.resetPassword(resetPayPasswordReqVO);
        return success();
    }

    @PostMapping("/test-pwd-verify")
    @ApiOperation("校验密码")
    public CommonResult<Boolean> testPwdVerify(@Valid @RequestBody AccountPwdVerifyDTO accountPwdVerifyDTO) {
        /*
        1、先要调用 getSecretInfo 获取公钥和random 111111值
        2、拿着公钥、random+密码  去加密 得到加密密文   111111admin123
        3、公钥、加密密文调用此接口 test-pwd-verify，
         */
        boolean flag = userAccountApi.verifyPayPassword(accountPwdVerifyDTO);
        return success(flag);
    }

}
