package com.hisun.kugga.module.pay.dal.mysql.merchant;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.QueryWrapperX;
import com.hisun.kugga.module.pay.controller.admin.merchant.vo.app.PayAppExportReqVO;
import com.hisun.kugga.module.pay.controller.admin.merchant.vo.app.PayAppPageReqVO;
import com.hisun.kugga.module.pay.dal.dataobject.merchant.PayAppDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PayAppMapper extends BaseMapperX<PayAppDO> {

    default PageResult<PayAppDO> selectPage(PayAppPageReqVO reqVO, Collection<Long> merchantIds) {
        return selectPage(reqVO, new QueryWrapperX<PayAppDO>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("remark", reqVO.getRemark())
                .eqIfPresent("pay_notify_url", reqVO.getPayNotifyUrl())
                .eqIfPresent("refund_notify_url", reqVO.getRefundNotifyUrl())
                .inIfPresent("merchant_id", merchantIds)
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default List<PayAppDO> selectList(PayAppExportReqVO reqVO, Collection<Long> merchantIds) {
        return selectList(new QueryWrapperX<PayAppDO>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("remark", reqVO.getRemark())
                .eqIfPresent("pay_notify_url", reqVO.getPayNotifyUrl())
                .eqIfPresent("refund_notify_url", reqVO.getRefundNotifyUrl())
                .inIfPresent("merchant_id", merchantIds)
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default List<PayAppDO> getListByMerchantId(Long merchantId) {
        return selectList(new LambdaQueryWrapper<PayAppDO>()
                .select(PayAppDO::getId, PayAppDO::getName)
                .eq(PayAppDO::getMerchantId, merchantId));
    }

    // TODO @aquan：方法名补充 ByMerchantId
    default Long selectCount(Long merchantId) {
        return selectCount(new LambdaQueryWrapper<PayAppDO>().eq(PayAppDO::getMerchantId, merchantId));
    }

}
