package com.hisun.kugga.duke.pay.service.useraccount;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.enums.SecretTypeEnum;
import com.hisun.kugga.duke.enums.email.EmailScene;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.AccountDetailReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.AccountDetailRspBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.AppDetailReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.AppDetailRspBody;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.ResetPayPasswordReqVO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.SetPayPasswordReqVO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.TradeFeeReqVO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.UpdatePayPasswordReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.useraccount.UserAccountDO;
import com.hisun.kugga.duke.pay.dal.mysql.useraccount.UserAccountMapper;
import com.hisun.kugga.duke.pay.service.leagueaccount.LeagueAccountService;
import com.hisun.kugga.duke.system.api.email.SendEmailApi;
import com.hisun.kugga.duke.system.api.email.dto.SendCodeReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.VerifyCodeReqDTO;
import com.hisun.kugga.duke.system.api.rsa.PasswordDecryptApi;
import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;
import com.hisun.kugga.duke.user.api.oauth2.DukeUserApi;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.duke.utils.RedisUtils;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import com.hisun.kugga.framework.common.util.date.DateTimeUtils;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.PAY_PASSWORD_SIX_NUMBER;
import static com.hisun.kugga.duke.common.CommonConstants.EN_US;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.util.amount.AmountUtil.HUNDRED;

/**
 * @author: zhou_xiong
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private static final String SET_PAY_PWD_EMIAL = "1";
    private static final String UPDATE_PAY_PWD_EMIAL = "2";
    private static final String RESET_PAY_PWD_EMIAL = "3";
    private static final String MIN = "0.01";

    public static final String SIX_PWD_VERIFY = "^\\d{6}$";
    public static final String PWD_ERROR_COUNT_PREFIX = "PWD_ERROR:";
    public static final int DEFAULT_COUNT = 5;

    @Value("${kugga.pay.wallet.platform-account}")
    private String platformAccount;
    @Resource
    private UserAccountMapper userAccountMapper;
    @Resource
    private SendEmailApi sendEmailApi;
    @Resource
    private DukeUserApi dukeUserApi;
    @Resource
    private WalletApi walletApi;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private PasswordDecryptApi decryptApi;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private LeagueAccountService leagueAccountService;


    @Override
    public void updateAccount(Long userId, BigDecimal amount) {
        int count = userAccountMapper.updateAccount(userId, amount);
        if (count <= 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.UPDATE_USER_ACCOUNT_FAILED);
        }
    }

    @Override
    public UserAccountDO getUserAccountByUserId(Long userId) {
        UserAccountDO userAccountDO = userAccountMapper.selectOne(new LambdaQueryWrapper<UserAccountDO>()
                .eq(UserAccountDO::getUserId, userId));
        if (ObjectUtil.isNull(userAccountDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.USER_ACCOUNT_NOT_EXISTS);
        }
        return userAccountDO;
    }

    @Override
    public Boolean getPwdFlag(Long userId) {
        UserAccountDO userAccountDO = getUserAccountByUserId(userId);
        return StrUtil.isNotEmpty(userAccountDO.getPayPassword());
    }

    public void verifyPayPassword(String payPassword) {
        boolean matches = Pattern.matches(SIX_PWD_VERIFY, payPassword);
        if (!matches) {
            throw exception(PAY_PASSWORD_SIX_NUMBER);
        }
    }

    @Override
    @CacheEvict(value = "cacheable:dukeUser", key = "#userId")
    public void setPassword(SetPayPasswordReqVO setPayPasswordReqVO, Long userId) {
        // 确认密码  密文传输不传确认密码了
        // 校验邮箱验证码
        verifyCode(setPayPasswordReqVO.getVerifyCode(), userId, EmailScene.SET_PAY_PASSWORD);
        //解密密码
        String password = decryptPassword(setPayPasswordReqVO.getPublicKey(), setPayPasswordReqVO.getPayPassword(), SecretTypeEnum.PAY);
        setPayPasswordReqVO.setPayPassword(password);

        verifyPayPassword(password);

        // 加密密码
        String encodePwd = passwordEncoder.encode(setPayPasswordReqVO.getPayPassword());
        // 更新
        updatePayPassword(userId, encodePwd);
    }

    private void updatePayPassword(Long userId, String encodePwd) {
        int count = userAccountMapper.update(null, new LambdaUpdateWrapper<UserAccountDO>()
                .set(UserAccountDO::getPayPassword, encodePwd)
                .eq(UserAccountDO::getUserId, userId));
        if (count <= 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.DB_UPDATE_FAILED);
        }
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

    @Override
    public void updatePassword(UpdatePayPasswordReqVO updatePayPasswordReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        String key = buildRedisKey(loginUserId);
        Integer errorCount = redisUtils.getInteger(key);
        if (ObjectUtil.isNotNull(errorCount)) {
            if (errorCount >= DEFAULT_COUNT) {
                ServiceException.throwServiceException(BusinessErrorCodeConstants.PAY_PWD_ERROR_COUNT_OVER_LIMIT);
            }
        }
        // 校验验证码
        verifyCode(updatePayPasswordReqVO.getVerifyCode(), loginUserId, EmailScene.UPDATE_PAY_PASSWORD);

        //解密新老密码
        String oldPassword = decryptPassword(updatePayPasswordReqVO.getPublicKey(), updatePayPasswordReqVO.getOldPayPassword(), SecretTypeEnum.PAY_UPDATE);
        String password = decryptPassword(updatePayPasswordReqVO.getPublicKey(), updatePayPasswordReqVO.getNewPayPassword(), SecretTypeEnum.PAY_UPDATE);
        updatePayPasswordReqVO.setOldPayPassword(oldPassword);
        updatePayPasswordReqVO.setNewPayPassword(password);

        verifyPayPassword(oldPassword);
        verifyPayPassword(password);

        // 校验旧密码
        UserAccountDO userAccountDO = getUserAccountByUserId(loginUserId);
        if (!passwordEncoder.matches(updatePayPasswordReqVO.getOldPayPassword()
                , userAccountDO.getPayPassword())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.OLD_PASSWORD_WRONG);
        }
        // 更新密码
        updatePayPassword(loginUserId, passwordEncoder.encode(updatePayPasswordReqVO.getNewPayPassword()));
    }

    @Override
    public void resetPassword(ResetPayPasswordReqVO resetPayPasswordReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        verifyCode(resetPayPasswordReqVO.getVerifyCode(), loginUserId, EmailScene.RESET_PAY_PASSWORD);

        //解密密码
        String password = decryptPassword(resetPayPasswordReqVO.getPublicKey(), resetPayPasswordReqVO.getNewPayPassword(), SecretTypeEnum.PAY);
        resetPayPasswordReqVO.setNewPayPassword(password);

        verifyPayPassword(password);

        // 更新密码
        updatePayPassword(loginUserId, passwordEncoder.encode(resetPayPasswordReqVO.getNewPayPassword()));
        // 删除redis错误次数
        redisUtils.delete(buildRedisKey(loginUserId));
    }

    private void verifyCode(String code, Long loginUserId, EmailScene emailScene) {
        VerifyCodeReqDTO verifyCodeReqDTO = new VerifyCodeReqDTO();
        verifyCodeReqDTO.setEmailScene(emailScene);
        verifyCodeReqDTO.setEmail(dukeUserApi.getUserById(loginUserId).getEmail());
        verifyCodeReqDTO.setVerifyCode(code);
        sendEmailApi.verifyCode(verifyCodeReqDTO);
    }

    @Override
    public void sendEmailCode(String emailType) {
        // 查询用户信息
        UserInfoRespDTO user = dukeUserApi.getUserById(SecurityFrameworkUtils.getLoginUserId());
        SendCodeReqDTO sendCodeReqDTO = new SendCodeReqDTO();
        sendCodeReqDTO.setTo(user.getEmail());
        sendCodeReqDTO.setLocale(EN_US);
        if (StrUtil.equals(emailType, SET_PAY_PWD_EMIAL)) {
            sendCodeReqDTO.setEmailScene(EmailScene.SET_PAY_PASSWORD);
        } else if (StrUtil.equals(emailType, UPDATE_PAY_PWD_EMIAL)) {
            sendCodeReqDTO.setEmailScene(EmailScene.UPDATE_PAY_PASSWORD);
        } else if (StrUtil.equals(emailType, RESET_PAY_PWD_EMIAL)) {
            sendCodeReqDTO.setEmailScene(EmailScene.RESET_PAY_PASSWORD);
        }
        // 发邮件
        sendEmailApi.sendCode(sendCodeReqDTO);
    }

    @Override
    public BigDecimal getBalance(String accountId) {
        AccountDetailReqBody accountDetailReqBody = new AccountDetailReqBody()
                .setAccount(accountId);
        AccountDetailRspBody accountDetailRspBody = walletApi.accountDetail(accountDetailReqBody);
        Integer balance = accountDetailRspBody.getBalance();
        // 分转成元
        return AmountUtil.fenToYuan(balance);
    }

    @Override
    public boolean verifyPayPassword(Long userId, String payPassword) {
        // 支付密码错误次数限制
        String key = buildRedisKey(userId);
        Integer errorCount = redisUtils.getInteger(key);
        if (ObjectUtil.isNotNull(errorCount)) {
            if (errorCount >= DEFAULT_COUNT) {
                ServiceException.throwServiceException(BusinessErrorCodeConstants.PAY_PWD_ERROR_COUNT_OVER_LIMIT);
            }
        } else {
            redisUtils.setForTimeCustom(key, 0, 24, TimeUnit.HOURS);
        }
        UserAccountDO userAccountDO = getUserAccountByUserId(userId);
        boolean matchFlag = passwordEncoder.matches(payPassword, userAccountDO.getPayPassword());
        if (!matchFlag) {
            redisUtils.increment(key);
        } else {
            redisUtils.delete(key);
        }
        return matchFlag;
    }

    @Override
    public BigDecimal tradeFee(TradeFeeReqVO tradeFeeReqVO) {
        if (tradeFeeReqVO.getAmount().compareTo(new BigDecimal(MIN)) <= 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.MIN_VALUE_ERROR);
        }
        AppDetailRspBody appDetailRspBody = walletApi.appDetail(new AppDetailReqBody());
        switch (tradeFeeReqVO.getTradeType()) {
            case CHARGE:
                return fee(tradeFeeReqVO.getAmount(), appDetailRspBody.getChargeFeeRate(),
                        appDetailRspBody.getChargeFeeFixed(), appDetailRspBody.getChargeFeeMax());
            case CONSUME:
                return fee(tradeFeeReqVO.getAmount(), appDetailRspBody.getConsumeFeeRate(),
                        appDetailRspBody.getConsumeFeeFixed(), appDetailRspBody.getConsumeFeeMax());
            case REFUND:
                return fee(tradeFeeReqVO.getAmount(), appDetailRspBody.getDrawbackFeeRate(),
                        appDetailRspBody.getDrawbackFeeFixed(), appDetailRspBody.getDrawbackFeeMax());
            case PAYOUT:
                return fee(tradeFeeReqVO.getAmount(), appDetailRspBody.getPayoutFeeRate(),
                        appDetailRspBody.getPayoutFeeFixed(), appDetailRspBody.getPayoutFeeMax());
            default:
                return BigDecimal.ZERO;
        }
    }

    private BigDecimal fee(BigDecimal amount, BigDecimal feeRate, BigDecimal feeFixed, BigDecimal feeMax) {
        // 金额*费率 + 固定费用
        BigDecimal fee = AmountUtil.add(AmountUtil.mul(amount, AmountUtil.div(feeRate, HUNDRED)), feeFixed);
        return fee.compareTo(feeMax) > 0 ? feeMax : fee;
    }

    private String buildRedisKey(Long userId) {
        return new StringBuilder(PWD_ERROR_COUNT_PREFIX).append(DateTimeUtils.getCurrentDateStr())
                .append(":").append(userId).toString();
    }

    @Override
    public String findWalletAccount(AccountType accountType, Long id) {
        switch (accountType) {
            case USER:
                return getUserAccountByUserId(id).getAccountId();
            case LEAGUE:
                return leagueAccountService.getLeagueAccountByLeagueId(id).getAccountId();
            case PLATFORM:
                return platformAccount;
            default:
                ServiceException.throwServiceException(BusinessErrorCodeConstants.UNSUPPORT_PAYER_TYPE);
                return null;
        }
    }

    @Override
    public void balanceEnough(BigDecimal totalAmount, String accountId) {
        BigDecimal balance = getBalance(accountId);
        if (totalAmount.compareTo(balance) > 0) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.USER_ACCOUNT_NOT_ENOUGH);
        }
    }
}
