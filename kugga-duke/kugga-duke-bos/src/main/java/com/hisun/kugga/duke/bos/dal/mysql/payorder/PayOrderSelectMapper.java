package com.hisun.kugga.duke.bos.dal.mysql.payorder;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.PayOrderExportReqVO;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.PayOrderPageReqVO;
import com.hisun.kugga.duke.bos.controller.admin.payorder.vo.PayOrderRespVO;
import com.hisun.kugga.duke.bos.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * 支付订单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PayOrderSelectMapper extends BaseMapperX<PayOrderDO> {

    /**
     * 分页查询订单
     *
     * @param page
     * @param appOrderNo
     * @param beginCreateTime
     * @param endCreateTime
     * @param status
     * @param orderType
     * @return
     */
    IPage<PayOrderRespVO> pageSelectOrders(Page page, @Param("appOrderNo") String appOrderNo, @Param("beginCreateTime") Date beginCreateTime,
                                           @Param("endCreateTime") Date endCreateTime, @Param("status") String status, @Param("orderType") String orderType);

    default PageResult<PayOrderDO> pageOrders(Page page, PayOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayOrderDO>()
                .eqIfPresent(PayOrderDO::getAppOrderNo, reqVO.getAppOrderNo())
                .eqIfPresent(PayOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PayOrderDO::getOrderType, reqVO.getOrderType())
                .eq(PayOrderDO::getDeleted, false)
                .orderByDesc(PayOrderDO::getCreateTime)
        );
    }

    default List<PayOrderDO> selectOrderList(PayOrderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PayOrderDO>()
                .eqIfPresent(PayOrderDO::getAppOrderNo, reqVO.getAppOrderNo())
                .eqIfPresent(PayOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PayOrderDO::getOrderType, reqVO.getOrderType())
                .between(PayOrderDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .eq(PayOrderDO::getDeleted, false)
                .orderByDesc(PayOrderDO::getCreateTime));
    }


}
