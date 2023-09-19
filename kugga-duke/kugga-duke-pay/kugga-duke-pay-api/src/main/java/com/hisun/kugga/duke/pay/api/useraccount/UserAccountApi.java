package com.hisun.kugga.duke.pay.api.useraccount;

import com.hisun.kugga.duke.pay.api.useraccount.dto.AccountPwdVerifyDTO;

/**
 * @author: zhou_xiong
 */
public interface UserAccountApi {
    /**
     * 判断用户是否设置支付密码
     *
     * @param userId
     * @return
     */
    boolean getPayPasswordFlag(Long userId);

    /**
     * 校验支付密码  ture: 校验通过，false: 校验不通过
     *
     * @param userId
     * @param payPassword
     * @return
     */
    boolean verifyPayPassword(Long userId, String payPassword);

    /**
     * 校验支付密码  ture: 校验通过，false: 校验不通过
     * 在密码
     *
     * @param pwdVerifyDTO
     * @return
     */
    boolean verifyPayPassword(AccountPwdVerifyDTO pwdVerifyDTO);
}
