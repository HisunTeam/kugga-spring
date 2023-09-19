package com.hisun.kugga.duke.bos.service.payorder;


import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.*;
import com.hisun.kugga.framework.common.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 支付订单 Service 接口
 *
 * @author 芋道源码
 */
public interface PayOrderSelectService {
    /**
     * 获取所有订单类型
     */
    Map<String, String> selectAllOrderType();

    /**
     * 获取所有订单状态
     */
    Map<String, String> selectAllStatus();
    /**
     * 获取所有订单状态
     */
    Map<String, String> selectAllChargeWithdrawStatus();

    /**
     * 分页查询订单以及子订单
     *
     * @param reqVO
     */
    PageResult<PayOrderRespVO> pagePayOrder(PayOrderPageReqVO reqVO);

    /**
     * 查询订单以及子订单
     *
     * @param exportReqVO
     */
    List<PayOrderExcelVO> getPayOrderList(PayOrderExportReqVO exportReqVO);

    /**
     * 分页查询充值和提现订单
     *
     * @param reqVO
     */
    PageResult<ChargeWithdrawOrderRespVO> pageChargeWithdrawOrder(PayOrderPageReqVO reqVO);

    /**
     * 查询充值和提现订单
     *
     * @param exportReqVO
     */
    List<ChargeWithdrawOrderExcelVO> listChargeWithdrawOrder(PayOrderExportReqVO exportReqVO);
}
