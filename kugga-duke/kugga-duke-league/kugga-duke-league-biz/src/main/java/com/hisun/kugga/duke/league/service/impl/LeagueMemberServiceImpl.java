package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.league.dal.dataobject.BonusUserDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueMemberDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMemberMapper;
import com.hisun.kugga.duke.league.enums.LeagueMemberLevelEnums;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.vo.LeagueMemberJoinVO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.LEAGUE_NOT_EXISTS;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Service
public class LeagueMemberServiceImpl implements LeagueMemberService {
    private static final int[] ADMIN_ARR = {0, 1};
    @Resource
    private LeagueMemberMapper leagueMemberMapper;

    @Override
    public boolean isAdmin(Long userId, Long leagueId) {
        LeagueMemberDO leagueMemberDO = leagueMemberMapper.selectOne(new LambdaQueryWrapper<LeagueMemberDO>()
                .eq(LeagueMemberDO::getUserId, userId)
                .eq(LeagueMemberDO::getLeagueId, leagueId)
                .select(LeagueMemberDO::getLevel));
        if (ObjectUtil.isNull(leagueMemberDO)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.LEAGUE_USER_NOT_EXISTS);
        }
        return ArrayUtil.contains(ADMIN_ARR, leagueMemberDO.getLevel());
    }

    @Override
    public List<Long> getUserJoinLeague(Long userId) {
        List<LeagueMemberDO> list = leagueMemberMapper.selectList(new LambdaQueryWrapperX<LeagueMemberDO>().eq(LeagueMemberDO::getUserId, userId));
        return list.stream().map(LeagueMemberDO::getLeagueId).collect(Collectors.toList());
    }

    @Override
    public Long getAdminIdByLeagueId(Long leagueId) {
        /*
        LambdaQueryWrapper<LeagueMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeagueMemberDO::getLeagueId,leagueId);
        wrapper.eq(LeagueMemberDO::getLevel, LeagueMemberLevelEnums.SUPER_ADM.getType());
        LeagueMemberDO leagueMemberDO = leagueMemberMapper.selectOne(wrapper);
        */
        LeagueMemberDO leagueMemberDO = leagueMemberMapper.selectOne(
                LeagueMemberDO::getLeagueId, leagueId,
                LeagueMemberDO::getLevel, LeagueMemberLevelEnums.SUPER_ADM.getType());
        if (ObjectUtil.isNull(leagueMemberDO)) {
            throw exception(LEAGUE_NOT_EXISTS);
        }
        return leagueMemberDO.getUserId();
    }

    @Override
    public List<Long> getLeagueAdmin(long leagueId) {
        LambdaQueryWrapper<LeagueMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LeagueMemberDO::getLeagueId, leagueId)
                .in(LeagueMemberDO::getLevel, 0, 1)
                .eq(LeagueMemberDO::getDeleted, false);
        List<LeagueMemberDO> list = leagueMemberMapper.selectList(wrapper);
        List<Long> userIdList = list.stream().map(LeagueMemberDO::getUserId).collect(Collectors.toList());
        return userIdList;
    }

    @Override
    public LeagueMemberDO getByLeagueIdAndUserId(Long leagueId, Long userId) {
        return leagueMemberMapper.getByLeagueIdAndUserId(leagueId, userId);
    }

    @Override
    public Integer joinLeague(LeagueMemberJoinVO joinVO) {
        LeagueMemberDO memberDO = leagueMemberMapper.getMemberByLeagueIdAndUserId(joinVO.getLeagueId(), joinVO.getUserId());
        boolean isExistMember = ObjectUtil.isNotNull(memberDO);
        //存在会员记录且delete为true的
        if (isExistMember && memberDO.getDeleted()) {
            return leagueMemberMapper.updateMemberJoinStatus(memberDO.getId());
        }

        LeagueMemberDO insertDo = new LeagueMemberDO();
        insertDo.setLeagueId(joinVO.getLeagueId());
        insertDo.setUserId(joinVO.getUserId());
        insertDo.setLevel(joinVO.getLevel());
        insertDo.setJoinType(joinVO.getJoinType());
        insertDo.setRelationUserId(joinVO.getRelationUserId());
        insertDo.setJoinTime(joinVO.getJoinTime());
        insertDo.setUpdateTime(joinVO.getUpdateTime());

        int res = leagueMemberMapper.insert(insertDo);
        if (res != 1) {
            log.info("插入公会信息表[duke_league]失败,需要插入数据如下[{}]", insertDo);
            throw exception(BusinessErrorCodeConstants.LEAGUE_INSERT_ERR);
        }
        return res;
    }

    @Override
    public List<BonusUserDO> canBonus(Long leagueId) {
        // 加入公会超过30天时间，最近30天有登录，成长值大于0
        return leagueMemberMapper.selectCanBonus(leagueId);
    }

    @Override
    public void growthThenLevel(GrowthDTO growthDTO) {
        leagueMemberMapper.growthThenLevel(growthDTO, LocalDateTime.now());
    }
}
