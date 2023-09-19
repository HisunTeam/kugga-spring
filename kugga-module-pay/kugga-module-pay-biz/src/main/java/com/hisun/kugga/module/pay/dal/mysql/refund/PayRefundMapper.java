package com.hisun.kugga.module.pay.dal.mysql.refund;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.QueryWrapperX;
import com.hisun.kugga.module.pay.controller.admin.refund.vo.PayRefundExportReqVO;
import com.hisun.kugga.module.pay.controller.admin.refund.vo.PayRefundPageReqVO;
import com.hisun.kugga.module.pay.dal.dataobject.refund.PayRefundDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayRefundMapper extends BaseMapperX<PayRefundDO> {

    default PageResult<PayRefundDO> selectPage(PayRefundPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<PayRefundDO>()
                .eqIfPresent("merchant_id", reqVO.getMerchantId())
                .eqIfPresent("app_id", reqVO.getAppId())
                .eqIfPresent("channel_code", reqVO.getChannelCode())
                .likeIfPresent("merchant_refund_no", reqVO.getMerchantRefundNo())
                .eqIfPresent("type", reqVO.getType())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("notify_status", reqVO.getNotifyStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default List<PayRefundDO> selectList(PayRefundExportReqVO reqVO) {
        return selectList(new QueryWrapperX<PayRefundDO>()
                .eqIfPresent("merchant_id", reqVO.getMerchantId())
                .eqIfPresent("app_id", reqVO.getAppId())
                .eqIfPresent("channel_code", reqVO.getChannelCode())
                .likeIfPresent("merchant_refund_no", reqVO.getMerchantRefundNo())
                .eqIfPresent("type", reqVO.getType())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("notify_status", reqVO.getNotifyStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default Long selectCount(Long appId, Integer status) {

        return selectCount(new LambdaQueryWrapper<PayRefundDO>()
                .eq(PayRefundDO::getAppId, appId)
                .eq(PayRefundDO::getStatus, status));
    }

    default PayRefundDO selectByReqNo(String reqNo) {
        return selectOne("req_no", reqNo);
    }

    default PayRefundDO selectByTradeNoAndMerchantRefundNo(String tradeNo, String merchantRefundNo) {
        return selectOne("trade_no", tradeNo, "merchant_refund_no", merchantRefundNo);
    }
}
