package com.hisun.kugga.duke.pay.dal.mysql.leagueaccount;

import com.hisun.kugga.duke.pay.dal.dataobject.leagueaccount.LeagueAccountDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 公会账户金额 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface LeagueAccountMapper extends BaseMapperX<LeagueAccountDO> {

    /**
     * 根据公会ID变更公会账户金额
     *
     * @param leagueId
     * @param amount
     * @return
     */
    int updateAccount(@Param("leagueId") Long leagueId, @Param("amount") BigDecimal amount);
}
