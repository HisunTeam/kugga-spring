package com.hisun.kugga.duke.bos.dal.mysql.withdraworder;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.ChargeWithdrawOrderRespVO;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.PayOrderExportReqVO;
import com.hisun.kugga.duke.bos.dal.dataobject.withdraworder.WithdrawOrderDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 提现订单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WithdrawMapper extends BaseMapperX<WithdrawOrderDO> {

    IPage<WithdrawOrderDO> pageSelectOrders(Page page, @Param("appOrderNo") String appOrderNo, @Param("beginCreateTime") Date beginCreateTime,
                                                      @Param("endCreateTime") Date endCreateTime, @Param("status") String status);


    default List<WithdrawOrderDO> selectOrderList(PayOrderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WithdrawOrderDO>()
                .eqIfPresent(WithdrawOrderDO::getAppOrderNo, reqVO.getAppOrderNo())
                .eqIfPresent(WithdrawOrderDO::getStatus, reqVO.getStatus())
                .between(WithdrawOrderDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(WithdrawOrderDO::getCreateTime));
    }
}
