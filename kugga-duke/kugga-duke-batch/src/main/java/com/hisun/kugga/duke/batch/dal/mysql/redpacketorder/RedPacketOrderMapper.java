package com.hisun.kugga.duke.batch.dal.mysql.redpacketorder;

import com.hisun.kugga.duke.batch.dal.dataobject.redpacketorder.RedPacketOrderDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.cursor.Cursor;

/**
 * 红包订单 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface RedPacketOrderMapper extends BaseMapperX<RedPacketOrderDO> {

    Cursor<RedPacketOrderDO> selectDraftOrders();
}
