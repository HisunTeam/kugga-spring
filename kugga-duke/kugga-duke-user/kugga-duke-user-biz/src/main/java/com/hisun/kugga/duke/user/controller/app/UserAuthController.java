package com.hisun.kugga.duke.user.controller.app;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.user.controller.vo.auth.*;
import com.hisun.kugga.duke.user.controller.vo.user.UserCreateReqVO;
import com.hisun.kugga.duke.user.service.user.UserAuthService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.security.config.SecurityProperties;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

@Api(tags = "A1-用户登录注册")
@RestController
@RequestMapping("/user/auth")
@Validated
@Slf4j
public class UserAuthController {

    @Resource
    private UserAuthService userAuthService;
    @Resource
    private SecurityProperties securityProperties;


    @PostMapping("/register")
    @ApiOperation("a.用户注册")
    public CommonResult<Long> registerUser(@Valid @RequestBody UserCreateReqVO createReqVO) {
        Long userId = userAuthService.registerUser(createReqVO);
        return success(userId);
    }

    @PostMapping("/email-login")
    @ApiOperation("b.用户登录  使用邮箱 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> emailLogin(@RequestBody @Valid AppAuthEmailLoginReqVO reqVO) {
        AppAuthLoginRespVO respVO = userAuthService.emailLogin(reqVO);
        return success(respVO);
    }

    @PostMapping("/email-login-test")
    @ApiOperation("a.用户登录-测试使用")
    public CommonResult<AppAuthLoginRespVO> emailLogin2(@RequestBody @Valid AppAuthEmailLoginReqVO reqVO) {
        AppAuthLoginRespVO respVO = userAuthService.emailLogin2(reqVO);
        return success(respVO);
    }

    @Deprecated
    @PostMapping("/resetPassword")
    @ApiOperation(value = "c.重置密码", notes = "用户忘记密码时使用")
    public CommonResult<Boolean> resetPassword(@RequestBody @Valid AppAuthResetPasswordReqVO reqVO) {
        userAuthService.resetPassword(reqVO);
        return success(true);
    }

    @PostMapping("/forgetPassword")
    @ApiOperation(value = "c.忘记密码", notes = "忘记密码-链接")
    public CommonResult<AppAuthLoginRespVO> forgetPassword(@RequestBody @Valid AppAuthForgetPasswordReqVO reqVO) {
        AppAuthLoginRespVO respVO = userAuthService.forgetPassword(reqVO);
        return success(respVO);
    }

    @PostMapping("/send-email-code")
    @ApiOperation(value = "d.发送邮箱证码")
    public CommonResult<Boolean> sendEmailCode(@RequestBody @Valid AppAuthEmailSendReqVO reqVO) {
        userAuthService.sendEmailCode(reqVO);
        return success(true);
    }

    @PostMapping("/send-email-link")
    @ApiOperation(value = "d.发送邮箱链接-忘记密码")
    public CommonResult<Boolean> sendEmailLink(@RequestBody @Valid AppAuthEmailSendReqVO reqVO) {
        userAuthService.sendEmailLink(reqVO);
        return success(true);
    }


    @PostMapping("/logout")
    @ApiOperation("e.登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            userAuthService.logout(token);
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @ApiOperation("f.刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    public CommonResult<AppAuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(userAuthService.refreshToken(refreshToken));
    }

}
