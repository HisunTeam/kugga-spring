package com.hisun.kugga.duke.league.dal.mysql;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueSubscribeDO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribePageReqVO;
import com.hisun.kugga.duke.league.vo.subscribe.SubscribeRespVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公会成员订阅 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueSubscribeMapper extends BaseMapperX<LeagueSubscribeDO> {

    LeagueSubscribeDO getByLeagueIdAndUserId(@Param("leagueId") Long leagueId, @Param("userId") Long userId);


    /**
     * 获取我的订阅记录
     *
     * @param pageParam
     * @param pageVO
     * @return
     */
    Page<SubscribeRespVO> getSubscribePageByUserId(@Param("pageParam") Page<SubscribeRespVO> pageParam,
                                                   @Param("pageVO") SubscribePageReqVO pageVO);
}
