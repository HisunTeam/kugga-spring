package com.hisun.kugga.duke.pay.convert.order;

import com.hisun.kugga.duke.pay.api.order.dto.OrderCreateReqDTO;
import com.hisun.kugga.duke.pay.controller.app.payorder.vo.OrderCreateReqVO;
import com.hisun.kugga.duke.pay.controller.app.payorder.vo.OrderRespVO;
import com.hisun.kugga.duke.pay.controller.app.payorder.vo.OrderUpdateReqVO;
import com.hisun.kugga.duke.pay.dal.dataobject.payorder.PayOrderDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单 Convert
 *
 * @author zhou_xiong
 */
@Mapper
public interface OrderConvert {

    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    PayOrderDO convert(OrderCreateReqVO bean);

    PayOrderDO convert(OrderUpdateReqVO bean);

    PayOrderDO convert(OrderCreateReqDTO bean);

    OrderRespVO convert(PayOrderDO bean);

    List<OrderRespVO> convertList(List<PayOrderDO> list);

    PageResult<OrderRespVO> convertPage(PageResult<PayOrderDO> page);
}
