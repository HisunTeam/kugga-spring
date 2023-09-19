package com.hisun.kugga.duke.pay.dal.mysql.useraccount;

import com.hisun.kugga.duke.pay.dal.dataobject.useraccount.UserAccountDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户账户金额 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface UserAccountMapper extends BaseMapperX<UserAccountDO> {

    int updateAccount(@Param("userId") Long userId, @Param("amount") BigDecimal amount);
}
