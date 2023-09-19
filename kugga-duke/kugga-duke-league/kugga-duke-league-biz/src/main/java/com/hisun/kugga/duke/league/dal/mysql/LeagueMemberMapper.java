package com.hisun.kugga.duke.league.dal.mysql;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.league.dal.dataobject.BonusUserDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.vo.LeagueMembersPageReqVO;
import com.hisun.kugga.duke.league.vo.LeagueMembersVO;
import com.hisun.kugga.duke.league.vo.UserLeaguesVO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公会成员 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LeagueMemberMapper extends BaseMapperX<LeagueMemberDO> {
    /**
     * 判断用户是否在公会内（可以判断一个人是否在一个公会,也可以判断多个人是不是同属一个公会）
     *
     * @param leagueId
     * @param users
     * @return
     */
    default Long isUserMember(Long leagueId, List<Long> users) {
        return selectCount(new LambdaQueryWrapperX<LeagueMemberDO>()
                .eq(LeagueMemberDO::getLeagueId, leagueId)
                .in(LeagueMemberDO::getUserId, users)
        );
    }

    /**
     * 查询用户是否为公会管理员
     *
     * @param leagueId
     * @param userId
     * @return
     */
    default LeagueMemberDO isLeagueAdmin(Long leagueId, Long userId) {
        return selectOne(new LambdaQueryWrapperX<LeagueMemberDO>()
                .eq(LeagueMemberDO::getLeagueId, leagueId)
                .eq(LeagueMemberDO::getUserId, userId)
        );
    }

    /**
     * 根据公会id和用户id查询 ,该方法只查询delete=false的
     *
     * @param leagueId
     * @param userId
     * @return
     */
    default LeagueMemberDO getByLeagueIdAndUserId(Long leagueId, Long userId) {
        return selectOne(new LambdaQueryWrapperX<LeagueMemberDO>()
                .eq(LeagueMemberDO::getLeagueId, leagueId)
                .eq(LeagueMemberDO::getUserId, userId)
        );
    }

    /**
     * 根据公会id、用户id查询 不管是否逻辑删除
     *
     * @param leagueId
     * @param userId
     * @return
     */
    LeagueMemberDO getMemberByLeagueIdAndUserId(@Param("leagueId") Long leagueId, @Param("userId") Long userId);


    /**
     * 分页查询公会成员列表
     *
     * @param page
     * @param reqVO
     * @return
     */
    IPage<LeagueMembersVO> pageLeagueMembers(Page<LeagueMembersPageReqVO> page, @Param("reqVO") LeagueMembersPageReqVO reqVO);

    /**
     * 分页查询用户公会列表
     *
     * @param page
     * @param userId
     * @param authFlag
     * @return
     */
    IPage<UserLeaguesVO> pageUserLeagues(Page<UserLeaguesVO> page, @Param("userId") Long userId, @Param("authFlag") Boolean authFlag);


    /**
     * 获取当前用户所管理的公会id
     *
     * @param userId
     * @param level
     * @return
     */
    List<Long> getLeagueAdminIds(@Param("userId") Long userId, @Param("level") Integer level);

    /**
     * 插入公会成员表 逻辑删除true->false
     *
     * @param id
     */
    default Integer updateMemberJoinStatus(Long id) {
        return updateById(new LeagueMemberDO().setId(id).setDeleted(false).setQuitTime(null));
    }

    /**
     * 查询符合发放红包规则的用户  加入公会超过30天时间，且最近30天有登录
     * @param leagueId
     * @return
     */
    List<BonusUserDO> selectCanBonus(@Param("leagueId") Long leagueId);

    /**
     * 增加成长值,升级
     * @param growthDTO
     */
    void growthThenLevel(@Param("growthDTO") GrowthDTO growthDTO, @Param("time") LocalDateTime localDateTime);
}
