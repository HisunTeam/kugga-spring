package com.hisun.kugga.duke.league.dal.mysql;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskPayTypeEnum;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
import com.hisun.kugga.duke.league.vo.notice.LeagueNoticeVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 16:41
 */
@Mapper
public interface LeagueNoticeMapper extends BaseMapperX<LeagueNoticeDO> {

    default int insertSmart(LeagueNoticeDO entity) {
        entity.setPayType(BigDecimal.ZERO.compareTo(entity.getAmount()) == 0 ? TaskPayTypeEnum.FREE : TaskPayTypeEnum.PAY);
        return insert(entity);
    }

    ;

    /**
     * 一个月内给这个人写推荐报告不能超过一次
     *
     * @param userId   写推荐报告认
     * @param byUserId 被推荐人
     * @return
     */
    default Long oneMonthRepetitionWriteReport(Long userId, Long byUserId) {
        return selectCount(new LambdaQueryWrapperX<LeagueNoticeDO>()
                .eq(LeagueNoticeDO::getType, LeagueNoticeTypeEnum.NOTICE_TYPE_2)
                .eq(LeagueNoticeDO::getUseUserId, userId)
                .eq(LeagueNoticeDO::getByUserId, byUserId)
                .ge(LeagueNoticeDO::getCreateTime, LocalDateTime.now().minusMonths(1L)));
    }

    /**
     * 查询写推荐报告,过期还款接单的数据(查询2周内的数据,避免查询压力过大)
     * @return
     */
/*    default List<LeagueNoticeDO> queryReportExpire() {
        return selectList(new LambdaQueryWrapperX<LeagueNoticeDO>()
                .between(LeagueNoticeDO::getCreateTime,LocalDateTime.now().withNano(0).minusWeeks(2),LocalDateTime.now().withNano(0))
                .eq(LeagueNoticeDO::getType, LeagueNoticeTypeEnum.NOTICE_TYPE_1)
                .eq(LeagueNoticeDO::getStatus, LeagueNoticeStatusEnum.NOTICE_STATUS_1)
                .le(LeagueNoticeDO::getExpiresTime, LocalDateTime.now().withNano(0))
                .orderByAsc(LeagueNoticeDO::getId).last("limit 1000"));
    }*/

    /**
     * 公告栏ID
     *
     * @param id
     * @return
     */
    int setExpiresTimeToNull(@Param("id") Long id);

    Page<LeagueNoticeVO> selectPageByLeagueId(Page<LeagueNoticeVO> page, @Param("id") Long leagueId);
}
