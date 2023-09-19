package com.hisun.kugga.duke.bos.service.payorder;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.*;
import com.hisun.kugga.duke.bos.dal.dataobject.chargeorder.ChargeOrderExtDO;
import com.hisun.kugga.duke.bos.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.duke.bos.dal.dataobject.payorder.PayOrderSubDO;
import com.hisun.kugga.duke.bos.dal.dataobject.payorderrefund.PayOrderRefundDO;
import com.hisun.kugga.duke.bos.dal.mysql.chargeorder.ChargeMapper;
import com.hisun.kugga.duke.bos.dal.mysql.payorder.PayOrderSelectMapper;
import com.hisun.kugga.duke.bos.dal.mysql.payorder.PayOrderSubSelectMapper;
import com.hisun.kugga.duke.bos.dal.mysql.payorderrefund.PaySubOrderRefundMapper;
import com.hisun.kugga.duke.bos.dal.mysql.withdraworder.WithdrawMapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.enums.*;
import com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付订单 Service 实现类
 *
 * @author lzt
 */
@Service
public class PayOrderSelectServiceImpl implements PayOrderSelectService {

    private static final Long THIRTY_DAYS = 30L;


    @Resource
    private PayOrderSelectMapper payOrderSelectMapper;
    @Resource
    private PayOrderSubSelectMapper payOrderSubSelectMapper;
    @Resource
    private PaySubOrderRefundMapper paySubOrderRefundMapper;
    @Resource
    private ChargeMapper chargeMapper;
    @Resource
    private WithdrawMapper withdrawMapper;

    @Override
    public Map<String, String> selectAllOrderType() {
        Map<String, String> orderTypeMap = new HashMap<>();

        for (OrderType orderType : OrderType.values()) {
            orderTypeMap.put(orderType.getTypeCode(), orderType.getZh());
        }
        return orderTypeMap;
    }

    @Override
    public Map<String, String> selectAllStatus() {
        Map<String, String> orderStatusMap = new HashMap<>();

        for (PayOrderStatus status : PayOrderStatus.values()) {
            orderStatusMap.put(status.getKey(), status.getDesc());
        }
        return orderStatusMap;
    }

    @Override
    public Map<String, String> selectAllChargeWithdrawStatus() {
        Map<String, String> orderStatusMap = new HashMap<>();

        for (ChargeOrderStatus status : ChargeOrderStatus.values()) {
            orderStatusMap.put(status.getKey(), status.getDesc());
        }
        for (WithdrawStatus status : WithdrawStatus.values()) {
            orderStatusMap.put(status.getKey(), status.getDesc());
        }
        return orderStatusMap;
    }

    @Override
    public PageResult<PayOrderRespVO> pagePayOrder(PayOrderPageReqVO reqVO) {

        Page page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<PayOrderRespVO> pageLeagues = payOrderSelectMapper.pageSelectOrders(page, reqVO.getAppOrderNo(), reqVO.getBeginCreateTime(), reqVO.getEndCreateTime(),
                reqVO.getStatus(), reqVO.getOrderType());
        List<PayOrderRespVO> collect = pageLeagues.getRecords().stream().map(payOrder -> {
            ArrayList<PaySubOrderVO> paySubOrderVOS = new ArrayList<>();
            List<PayOrderSubDO> payOrderSubDOS = payOrderSubSelectMapper.querySubOrder(payOrder.getAppOrderNo());
            if (!ObjectUtil.isNull(payOrderSubDOS)) {
                payOrderSubDOS.forEach(subOrder -> {
                    PaySubOrderVO paySubOrderVO = new PaySubOrderVO().setAppOrderNo(String.valueOf(subOrder.getId()))
                            .setOrderType(subOrder.getOrderType().getZh())
                            .setPayChannel(payOrder.getPayChannel().getDesc())
                            .setCreateTime(subOrder.getCreateTime())
                            .setPayAmount(subOrder.getAmount())
                            .setStatus(subOrder.getStatus().getDesc());
                    paySubOrderVOS.add(paySubOrderVO);
                });
            }
            List<PayOrderRefundDO> payOrderRefundDOS = paySubOrderRefundMapper.querySubRefundOrder(payOrder.getAppOrderNo());
            if (!ObjectUtil.isNull(payOrderRefundDOS)) {
                payOrderRefundDOS.forEach(subOrder -> {
                    PaySubOrderVO paySubOrderVO = new PaySubOrderVO().setAppOrderNo(String.valueOf(subOrder.getId()))
                            .setOrderType(payOrder.getOrderType().getZh())
                            .setPayChannel(payOrder.getPayChannel().getDesc())
                            .setCreateTime(subOrder.getCreateTime())
                            .setPayAmount(subOrder.getAmount())
                            .setStatus(subOrder.getStatus().getDesc());
                    paySubOrderVOS.add(paySubOrderVO);
                });
            }
            if (ObjectUtil.isNotNull(paySubOrderVOS)) {
                payOrder.setChildren(paySubOrderVOS);
            }
            return payOrder;
        }).collect(Collectors.toList());
        return new PageResult<>(collect, pageLeagues.getTotal());
    }

    @Override
    public List<PayOrderExcelVO> getPayOrderList(PayOrderExportReqVO exportReqVO) {
        //判断时间是否在一个月内
        if (exportReqVO.getBeginCreateTime().isBefore(exportReqVO.getEndCreateTime().minusDays(THIRTY_DAYS))) {
            throw ServiceExceptionUtil.exception(BusinessErrorCodeConstants.ONE_MONTH_DATA);
        }
        List<PayOrderDO> payOrderDOS = payOrderSelectMapper.selectOrderList(exportReqVO);
        List<PayOrderExcelVO> excelVOS = new ArrayList<>();
        //遍历订单查询子订单，加到excelVOS
        payOrderDOS.forEach(payOrder -> {
            excelVOS.add(new PayOrderExcelVO()
                    .setAppOrderNo(payOrder.getAppOrderNo())
                    .setCreateTime(payOrder.getCreateTime())
                    .setPayAmount(payOrder.getPayAmount())
                    .setPayChannel(payOrder.getPayChannel().getDesc())
                    .setStatus(payOrder.getStatus().getDesc())
                    .setOrderType(payOrder.getOrderType().getZh())
            );
            List<PayOrderSubDO> payOrderSubDOS = payOrderSubSelectMapper.querySubOrder(payOrder.getAppOrderNo());
            if (!ObjectUtil.isNull(payOrderSubDOS)) {
                payOrderSubDOS.forEach(subOrder -> {
                    excelVOS.add(new PayOrderExcelVO()
                            .setAppOrderNo(subOrder.getAppOrderNo())
                            .setId(subOrder.getId())
                            .setCreateTime(subOrder.getCreateTime())
                            .setPayAmount(subOrder.getAmount())
                            .setPayChannel(payOrder.getPayChannel().getDesc())
                            .setStatus(subOrder.getStatus().getDesc())
                            .setOrderType(subOrder.getOrderType().getZh())
                    );
                });
            }
            List<PayOrderRefundDO> payOrderRefundDOS = paySubOrderRefundMapper.querySubRefundOrder(payOrder.getAppOrderNo());
            if (!ObjectUtil.isNull(payOrderRefundDOS)) {
                payOrderRefundDOS.forEach(subOrder -> {
                    excelVOS.add(new PayOrderExcelVO()
                            .setAppOrderNo(subOrder.getAppOrderNo())
                            .setId(subOrder.getId())
                            .setCreateTime(subOrder.getCreateTime())
                            .setPayAmount(subOrder.getAmount())
                            .setPayChannel(payOrder.getPayChannel().getDesc())
                            .setStatus(subOrder.getStatus().getDesc())
                            .setOrderType(payOrder.getOrderType().getZh())
                    );
                });
            }
        });
        return excelVOS;
    }

    @Override
    public PageResult<ChargeWithdrawOrderRespVO> pageChargeWithdrawOrder(PayOrderPageReqVO reqVO) {
        Page page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());

        IPage<ChargeOrderExtDO> pageLeagues = chargeMapper.pageSelectALlOrders(page, reqVO.getAppOrderNo(), reqVO.getBeginCreateTime(), reqVO.getEndCreateTime(),
                reqVO.getStatus(), reqVO.getOrderType());
        List<ChargeWithdrawOrderRespVO> collect = pageLeagues.getRecords().stream().map(
                payOrder -> new ChargeWithdrawOrderRespVO()
                        .setOrderType(ChargeWithdrawEnum.getEnumByCode(payOrder.getOrderType()).getMsg())
                        .setAppOrderNo(payOrder.getAppOrderNo())
                        .setStatus(payOrder.getStatus())
                        .setAmount(payOrder.getAmount())
                        .setCreateTime(payOrder.getCreateTime())
                        .setChannel(payOrder.getChargeChannel())
        ).collect(Collectors.toList());
        return new PageResult<>(collect, pageLeagues.getTotal());

    }

    @Override
    public List<ChargeWithdrawOrderExcelVO> listChargeWithdrawOrder(PayOrderExportReqVO reqVO) {
        //判断时间是否在一个月内
        if (reqVO.getBeginCreateTime().isBefore(reqVO.getEndCreateTime().minusDays(THIRTY_DAYS))) {
            throw ServiceExceptionUtil.exception(BusinessErrorCodeConstants.ONE_MONTH_DATA);
        }
        List<ChargeOrderExtDO> chargeOrderDOS = chargeMapper.listSelectOrders(reqVO.getAppOrderNo(), reqVO.getBeginCreateTime(), reqVO.getEndCreateTime(),
                reqVO.getStatus(), reqVO.getOrderType());
        List<ChargeWithdrawOrderExcelVO> excelVOS = chargeOrderDOS.stream().map(chargeOrderDO -> {
            return new ChargeWithdrawOrderExcelVO().setAppOrderNo(chargeOrderDO.getAppOrderNo())
                    .setCreateTime(chargeOrderDO.getCreateTime())
                    .setAmount(chargeOrderDO.getAmount())
                    .setChannel(chargeOrderDO.getChargeChannel())
                    .setStatus(chargeOrderDO.getStatus())
                    .setOrderType(ChargeWithdrawEnum.getEnumByCode(chargeOrderDO.getOrderType()).getMsg());
        }).collect(Collectors.toList());
        return excelVOS;
    }
}
