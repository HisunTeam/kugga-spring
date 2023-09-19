package com.hisun.kugga.duke.pay.service.chargeorder;

import com.hisun.kugga.duke.pay.controller.app.charge.vo.ChargeDetailRspVO;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.PreChargeReqVO;
import com.hisun.kugga.duke.pay.controller.app.charge.vo.PreChargeRspVO;

/**
 * @author: zhou_xiong
 */
public interface ChargeService {
    /**
     * 预充值
     *
     * @param preChargeReqVO
     * @return
     */
    PreChargeRspVO preCharge(PreChargeReqVO preChargeReqVO);

    /**
     * 查询订单状态
     *
     * @param walletOrderNo
     * @return
     */
    ChargeDetailRspVO getChargeDetail(String walletOrderNo);

    /**
     * 取消订单
     *
     * @param walletOrderNo
     */
    void cancel(String walletOrderNo);
}
