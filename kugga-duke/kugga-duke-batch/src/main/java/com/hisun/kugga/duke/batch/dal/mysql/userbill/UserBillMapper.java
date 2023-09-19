package com.hisun.kugga.duke.batch.dal.mysql.userbill;

import com.hisun.kugga.duke.batch.dal.dataobject.userbill.UserBillDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账单 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface UserBillMapper extends BaseMapperX<UserBillDO> {

}
