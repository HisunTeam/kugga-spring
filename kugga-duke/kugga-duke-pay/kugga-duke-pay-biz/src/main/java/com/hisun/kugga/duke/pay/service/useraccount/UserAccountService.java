package com.hisun.kugga.duke.pay.service.useraccount;

import com.hisun.kugga.duke.enums.AccountType;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.ResetPayPasswordReqVO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.SetPayPasswordReqVO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.TradeFeeReqVO;
import com.hisun.kugga.duke.pay.controller.app.useraccount.vo.UpdatePayPasswordReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.useraccount.UserAccountDO;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
public interface UserAccountService {
    /**
     * 更新账户金额
     *
     * @param userId
     * @param amount
     */
    void updateAccount(Long userId, BigDecimal amount);

    /**
     * 通过userId查找
     *
     * @param userId
     * @return
     */
    UserAccountDO getUserAccountByUserId(Long userId);

    /**
     * 判断用户是否设置支付密码
     *
     * @param userId
     * @return
     */
    Boolean getPwdFlag(Long userId);

    /**
     * 设置支付密码
     *
     * @param setPayPasswordReqVO
     */
    void setPassword(SetPayPasswordReqVO setPayPasswordReqVO, Long userId);

    /**
     * 修改支付密码
     *
     * @param updatePayPasswordReqVO
     */
    void updatePassword(UpdatePayPasswordReqVO updatePayPasswordReqVO);

    /**
     * 重置支付密码
     *
     * @param resetPayPasswordReqVO
     */
    void resetPassword(ResetPayPasswordReqVO resetPayPasswordReqVO);

    /**
     * 修改/重置支付密码发送邮箱验证码
     *
     * @param emailType
     */
    void sendEmailCode(String emailType);


    /**
     * 查询钱包，获取当前登录用户的余额
     *
     * @param accountId
     * @return
     */
    BigDecimal getBalance(String accountId);

    /**
     * 校验支付密码
     *
     * @param userId
     * @param payPassword
     * @return
     */
    boolean verifyPayPassword(Long userId, String payPassword);

    /**
     * 交易手续费计算
     *
     * @param tradeFeeReqVO
     * @return
     */
    BigDecimal tradeFee(TradeFeeReqVO tradeFeeReqVO);

    /**
     * 通过账户类型和id 查询钱包账户
     *
     * @param accountType
     * @param id
     * @return
     */
    String findWalletAccount(AccountType accountType, Long id);

    /**
     * 判断余额是否足够
     *
     * @param totalAmount
     * @param accountId
     */
    void balanceEnough(BigDecimal totalAmount, String accountId);
}
