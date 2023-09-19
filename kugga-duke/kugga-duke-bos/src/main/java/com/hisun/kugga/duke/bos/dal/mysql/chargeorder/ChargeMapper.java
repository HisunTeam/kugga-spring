package com.hisun.kugga.duke.bos.dal.mysql.chargeorder;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.PayOrderExportReqVO;
import com.hisun.kugga.duke.bos.dal.dataobject.chargeorder.ChargeOrderDO;
import com.hisun.kugga.duke.bos.dal.dataobject.chargeorder.ChargeOrderExtDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 充值订单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ChargeMapper extends BaseMapperX<ChargeOrderDO> {

    IPage<ChargeOrderDO> pageSelectOrders(Page page, @Param("appOrderNo") String appOrderNo, @Param("beginCreateTime") Date beginCreateTime,
                                          @Param("endCreateTime") Date endCreateTime, @Param("status") String status);

    IPage<ChargeOrderExtDO> pageSelectALlOrders(Page page, @Param("appOrderNo") String appOrderNo, @Param("beginCreateTime") Date beginCreateTime,
                                                @Param("endCreateTime") Date endCreateTime, @Param("status") String status, @Param("orderType") String orderType);

    List<ChargeOrderExtDO> listSelectOrders(@Param("appOrderNo") String appOrderNo, @Param("beginCreateTime") LocalDateTime beginCreateTime,
                                            @Param("endCreateTime") LocalDateTime endCreateTime, @Param("status") String status, @Param("orderType") String orderType);

    default List<ChargeOrderDO> selectOrderList(PayOrderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ChargeOrderDO>()
                .eqIfPresent(ChargeOrderDO::getAppOrderNo, reqVO.getAppOrderNo())
                .eqIfPresent(ChargeOrderDO::getStatus, reqVO.getStatus())
                .between(ChargeOrderDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ChargeOrderDO::getCreateTime));
    }
}
