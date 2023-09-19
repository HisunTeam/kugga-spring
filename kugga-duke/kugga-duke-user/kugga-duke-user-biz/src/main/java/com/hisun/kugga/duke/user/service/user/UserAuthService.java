package com.hisun.kugga.duke.user.service.user;

import com.hisun.kugga.duke.user.controller.vo.auth.*;
import com.hisun.kugga.duke.user.controller.vo.user.UserCreateReqVO;

/**
 * 会员的认证 Service 接口
 * <p>
 * 提供用户的账号密码登录、token 的校验等认证相关的功能
 *
 * @author 芋道源码
 */
public interface UserAuthService {

    /**
     * 用户注册
     *
     * @param createReqVO
     * @return
     */
    Long registerUser(UserCreateReqVO createReqVO);

    /**
     * 邮箱+密码登录
     *
     * @param reqVO
     * @return
     */
    AppAuthLoginRespVO emailLogin(AppAuthEmailLoginReqVO reqVO);

    /**
     * 邮箱+密码登录
     *
     * @param reqVO
     * @return
     */
    AppAuthLoginRespVO emailLogin2(AppAuthEmailLoginReqVO reqVO);

    /**
     * 忘记密码
     *
     * @param userReqVO 用户请求实体类
     */
    void resetPassword(AppAuthResetPasswordReqVO userReqVO);

    /**
     * @param reqVO
     */
    AppAuthLoginRespVO forgetPassword(AppAuthForgetPasswordReqVO reqVO);

    /**
     * 发送邮箱验证码
     *
     * @param reqVO
     */
    void sendEmailCode(AppAuthEmailSendReqVO reqVO);

    /**
     * 发送邮箱链接
     *
     * @param reqVO
     */
    void sendEmailLink(AppAuthEmailSendReqVO reqVO);

    /**
     * 基于 token 退出登录
     *
     * @param token token
     */
    void logout(String token);


    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AppAuthLoginRespVO refreshToken(String refreshToken);


}
