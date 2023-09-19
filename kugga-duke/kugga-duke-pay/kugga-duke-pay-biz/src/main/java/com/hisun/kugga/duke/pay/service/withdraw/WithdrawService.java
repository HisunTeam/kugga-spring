package com.hisun.kugga.duke.pay.service.withdraw;

import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawDetailRspVO;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawToPayPalReqVO;
import com.hisun.kugga.duke.pay.controller.app.withdraw.vo.WithdrawToPayPalRspVO;

/**
 * @author: zhou_xiong
 */
public interface WithdrawService {
    /**
     * 提现至PayPal
     *
     * @param withdrawToPayPalReqVO
     * @return
     */
    WithdrawToPayPalRspVO toPayPal(WithdrawToPayPalReqVO withdrawToPayPalReqVO);

    /**
     * 查询提现状态
     *
     * @param walletOrderNo
     * @return
     */
    WithdrawDetailRspVO getWithdrawDetail(String walletOrderNo);
}
