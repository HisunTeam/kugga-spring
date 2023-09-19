package com.hisun.kugga.duke.user.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.enums.LoginLogTypeEnum;
import com.hisun.kugga.duke.enums.LoginResultEnum;
import com.hisun.kugga.duke.enums.SecretTypeEnum;
import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.system.api.email.SendEmailApi;
import com.hisun.kugga.duke.system.api.email.dto.GeneralEmailReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.SendCodeReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.VerifyCodeReqDTO;
import com.hisun.kugga.duke.system.api.rsa.KeyPairApi;
import com.hisun.kugga.duke.system.api.rsa.PasswordDecryptApi;
import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;
import com.hisun.kugga.duke.user.api.oauth2.OAuth2TokenApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.hisun.kugga.duke.user.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.hisun.kugga.duke.user.common.UserStateEnum;
import com.hisun.kugga.duke.user.controller.vo.auth.*;
import com.hisun.kugga.duke.user.controller.vo.user.UserCreateReqVO;
import com.hisun.kugga.duke.user.convert.AuthConvert;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.duke.user.dal.mysql.DukeUserMapper;
import com.hisun.kugga.duke.user.properties.UserProperties;
import com.hisun.kugga.duke.user.service.user.DukeUserService;
import com.hisun.kugga.duke.user.service.user.UserAuthService;
import com.hisun.kugga.framework.common.enums.CommonStatusEnum;
import com.hisun.kugga.framework.common.enums.UserTypeEnum;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.monitor.TracerUtils;
import com.hisun.kugga.framework.common.util.servlet.ServletUtils;
import com.hisun.kugga.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.hisun.kugga.module.system.enums.oauth2.OAuth2ClientConstants;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.duke.common.CommonConstants.EN_US;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.util.servlet.ServletUtils.getClientIP;

/**
 * 会员的认证 Service 接口
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

    private static final String RESISTER_PREFIX = "user:register:";
    private static final String RESET_PREFIX = "user:resetPwd:";
    private static final String USERNAME = "duke";

    private static final Integer MAX_USER_NUM = 999999;

    @Value("${duke.user.region-prefix:00}")
    private String regionPrefix;
    @Value("${duke.user.forget-pwd-link-prefix:}")
    private String forgetPwdLinkPrefix;

    @Resource
    private UserProperties userProperties;
    @Resource
    private DukeUserService userService;
    @Resource
    private OAuth2TokenApi oauth2TokenApi;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private KeyPairApi keyPairApi;
    @Resource
    private PasswordDecryptApi decryptApi;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private DukeUserMapper userMapper;
    @Resource
    private SendEmailApi sendEmailApi;


    @Override
    public Long registerUser(UserCreateReqVO createReqVO) {
        //密码密文时不校验确认密码了
        /*if (ObjectUtil.notEqual(createReqVO.getPassword(),createReqVO.getEnsurePwd())){
            throw exception(PASSWORD_ENSURE_NOT_MATCH);
        }*/

        if (!validateEmailCaptcha(createReqVO.getEmail(), EmailScene.USER_REGISTER, createReqVO.getCaptcha())) {
            throw exception(CODE_EXPIRED);
        }
        //邮箱统一存储小写
        createReqVO.setEmail(createReqVO.getEmail().toLowerCase());
        // 判断用户是否存在
        UserDO existUser = userMapper.selectByEmail(createReqVO.getEmail());
        if (existUser != null) {
            throw exception(USER_EMAIL_EXISTS);
        }

        //把密文解密为原文
        String password = decryptPassword(createReqVO.getPublicKey(), createReqVO.getPassword(), SecretTypeEnum.LOGIN);
        createReqVO.setPassword(password);
        //生成序列号
        Integer num = generateNum();

        String username = regionPrefix + USERNAME + getUserNum(num);
        createReqVO.setUsername(username);
        Long userId = userService.createUser(createReqVO);
        return userId;
    }


    @Override
    public AppAuthLoginRespVO emailLogin(AppAuthEmailLoginReqVO reqVO) {
        Optional.ofNullable(reqVO.getPassword()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "password"));
        Optional.ofNullable(reqVO.getPublicKey()).orElseThrow(() -> new ServiceException(ILLEGAL_PARAMS, "publicKey"));

        //把密文解密为原文
        String password = decryptPassword(reqVO.getPublicKey(), reqVO.getPassword(), SecretTypeEnum.LOGIN);
        reqVO.setPassword(password);
        // 使用邮箱 + 密码登录
        UserDO user = loginByEmailAndPassword(reqVO.getEmail(), reqVO.getPassword());

        return loginEnd(user);
    }


    @Override
    public AppAuthLoginRespVO emailLogin2(AppAuthEmailLoginReqVO reqVO) {
        // 使用邮箱 + 密码登录
        UserDO user = loginByEmailAndPassword(reqVO.getEmail(), reqVO.getPassword());

        return loginEnd(user);
    }


    @Override
    public void resetPassword(AppAuthResetPasswordReqVO reqVO) {
        /*if (ObjectUtil.notEqual(reqVO.getPassword(),reqVO.getEnsurePwd())){
            throw exception(PASSWORD_ENSURE_NOT_MATCH);
        }*/

        this.validateEmailCaptcha(reqVO.getEmail(), EmailScene.USER_RESET_PASSWORD, reqVO.getCaptcha());

        UserDO user = getUserByEmail(reqVO.getEmail());

        //把密文解密为原文
        String password = decryptPassword(reqVO.getPublicKey(), reqVO.getPassword(), SecretTypeEnum.LOGIN);
        reqVO.setPassword(password);

        // 更新密码
        userMapper.updateById(UserDO.builder().id(user.getId())
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    @Override
    public AppAuthLoginRespVO forgetPassword(AppAuthForgetPasswordReqVO reqVO) {
        UserDO user = userMapper.selectById(reqVO.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        //token值校验
        String redisToken = stringRedisTemplate.opsForValue().get(EmailScene.FORGET_PASSWORD.getPrefix() + user.getEmail());
        if (ObjectUtil.notEqual(redisToken, reqVO.getToken())) {
            throw exception(USER_FORGET_PWD_TOKEN_ERROR);
        }

        stringRedisTemplate.delete(EmailScene.FORGET_PASSWORD.getPrefix() + user.getEmail());

        //把密文解密为原文
        String password = decryptPassword(reqVO.getPublicKey(), reqVO.getPassword(), SecretTypeEnum.LOGIN);
        reqVO.setPassword(password);

        // 更新密码
        userMapper.updateById(UserDO.builder().id(user.getId())
                .password(passwordEncoder.encode(reqVO.getPassword())).build());

        //修改密码后直接登录
        return loginEnd(user);
    }

    @Override
    public void sendEmailCode(AppAuthEmailSendReqVO reqVO) {
        SendCodeReqDTO reqDTO = new SendCodeReqDTO();
        reqDTO.setEmailScene(reqVO.getEmailScene());
        reqDTO.setTo(reqVO.getEmail());
        reqDTO.setLocale(EN_US);
        sendEmailApi.sendCode(reqDTO);
    }

    @Override
    public void sendEmailLink(AppAuthEmailSendReqVO reqVO) {
        if (ObjectUtil.isEmpty(reqVO.getEmail())) {
            throw exception(EMAIL_NOT_NULL);
        }
        UserDO user = getUserByEmail(reqVO.getEmail());

        String token = IdUtil.fastSimpleUUID();

        stringRedisTemplate.opsForValue().set(EmailScene.FORGET_PASSWORD.getPrefix() + reqVO.getEmail(), token, 30L, TimeUnit.MINUTES);

        //https://www.hisunpay66.com:8020/forget/password?userId=1&token=abc
        String url = forgetPwdLinkPrefix + "?userId=" + user.getId() + "&token=" + token;
        GeneralEmailReqDTO emailReq = new GeneralEmailReqDTO()
                .setEmailScene(EmailScene.FORGET_PASSWORD)
                .setTo(Collections.singletonList(reqVO.getEmail()))
                .setLocale(EN_US)
                .setReplaceValues(CollUtil.newArrayList(url));

        //发送邀请邮件
        sendEmailApi.sendEmail(emailReq);
    }

    @NotNull
    private UserDO getUserByEmail(String email) {
        UserDO user = userMapper.selectByEmail(email);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }


    /**
     * @param publicKey
     * @param password
     * @param type
     * @return
     */
    private String decryptPassword(String publicKey, String password, SecretTypeEnum type) {
        DecryptDTO decryptDTO = new DecryptDTO()
                .setPublicKey(publicKey)
                .setPassword(password)
                .setType(type);
        return decryptApi.decrypt(decryptDTO);
    }

    @NotNull
    private UserAccountVo selectAccountByUserId(UserDO user) {
        UserAccountVo userAccountVo = userMapper.selectAccountByUserId(new UserAccountVo().setUserId(user.getId()));
        if (ObjectUtil.isNull(userAccountVo)) {
            throw exception(USER_ACCOUNT_NOT_EXIST);
        }
        return userAccountVo;
    }

    /**
     * 登陆后获取token 修改
     *
     * @param user
     * @return
     */
    @NotNull
    private AppAuthLoginRespVO loginEnd(UserDO user) {
        UserAccountVo userAccountVo = selectAccountByUserId(user);
        // 创建 Token 令牌，记录登录日志
        AppAuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccess(user, userAccountVo.getAccountId(), LoginLogTypeEnum.LOGIN_EMAIL);
        // 更新登录时间
        userService.updateUserLogin(user.getId(), getClientIP());
        return tokenAfterLoginSuccess;
    }

    private AppAuthLoginRespVO createTokenAfterLoginSuccess(UserDO user, String userAccountId, LoginLogTypeEnum logType) {
        // 创建Token令牌
        OAuth2AccessTokenCreateReqDTO auth2AccessTokenCreateReqDTO = new OAuth2AccessTokenCreateReqDTO();
        auth2AccessTokenCreateReqDTO.setUserId(user.getId());
        auth2AccessTokenCreateReqDTO.setUserType(getUserType().getValue());
        auth2AccessTokenCreateReqDTO.setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        auth2AccessTokenCreateReqDTO.setLastName(user.getLastName());
        auth2AccessTokenCreateReqDTO.setFirstName(user.getFirstName());
        auth2AccessTokenCreateReqDTO.setAccountId(userAccountId);
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(auth2AccessTokenCreateReqDTO);
        // 构建返回结果
        AppAuthLoginRespVO respVO = new AppAuthLoginRespVO();
        BeanUtils.copyProperties(accessTokenRespDTO, respVO);
        return respVO;
    }


    /**
     * 生成自增序列号
     *
     * @return
     */
    private Integer generateNum() {
        //99duke****** 账号唯一 重试
        //num id序号，count 执行次数，10次异常报错
        Integer num = 0;
        Integer count = 0;
        int addRes;
        do {
            count = validateRetryCount(count);
            //获取区号99的序号，更新99序号+1，如果更新失败，则重试
            num = userMapper.selectNumByRegionPrefix(regionPrefix);
            if (num >= 999999) {
                //最大只能有六位数
                throw exception(USER_NUMBER_COUNT_MAX);
            }
            //序号+1
            addRes = userMapper.updateAtGenAdd(regionPrefix, num);

        } while (addRes <= 0);
        return num;
    }

    /**
     * 用户重试次数不能超过10次
     *
     * @param count
     * @return
     */
    @NotNull
    private Integer validateRetryCount(Integer count) {
        count++;
        if (count >= 10) {
            throw exception(USER_REGISTER_COUNT_MAX);
        }
        return count;
    }

    public String getUserNum(Integer num) {
        if (num >= MAX_USER_NUM) {
            throw exception(USER_COUNT_MAX, MAX_USER_NUM);
        }
        if (num < 10) {
            return "00000" + num;
        } else if (num < 100) {
            return "0000" + num;
        } else if (num < 1000) {
            return "000" + num;
        } else if (num < 10000) {
            return "00" + num;
        } else if (num < 100000) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }


    /**
     * 校验验证码是否正确
     *
     * @param email
     * @param scene
     * @param captcha
     * @return
     */
    private boolean validateEmailCaptcha(String email, EmailScene scene, String captcha) {
        VerifyCodeReqDTO verifyCodeReqDTO = new VerifyCodeReqDTO();
        verifyCodeReqDTO.setEmail(email);
        verifyCodeReqDTO.setEmailScene(scene);
        verifyCodeReqDTO.setVerifyCode(captcha);
        return sendEmailApi.verifyCode(verifyCodeReqDTO);
    }


    private UserDO loginByMobileAndPassword(String mobile, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_MOBILE;
        // 校验账号是否存在
        UserDO user = userService.getUserByMobile(mobile);
        if (user == null) {
            createLoginLog(null, mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private UserDO loginByEmailAndPassword(String email, String password) {
        // 校验账号是否存在
        UserDO user = userService.getUserByEmail(email);
        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), UserStateEnum.NORMAL.getCode())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    /**
     * 插入登录日志 区分是邮箱还是手机 通过logType区分
     *
     * @param userId
     * @param mobileOrEmail
     * @param logType
     * @param loginResult
     */
    private void createLoginLog(Long userId, String mobileOrEmail, LoginLogTypeEnum logType, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(mobileOrEmail);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(getClientIP());
        reqDTO.setResult(loginResult.getResult());
//        loginLogApi.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, getClientIP());
        }
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.removeAccessToken(token);
        if (accessTokenRespDTO == null) {
            return;
        }
    }


    @Override
    public AppAuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenRespDTO accessTokenDO = oauth2TokenApi.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }


    private String getMobile(Long userId) {
        if (userId == null) {
            return null;
        }
        UserDO user = userService.getUser(userId);
        return user != null ? user.getPhone() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

}
