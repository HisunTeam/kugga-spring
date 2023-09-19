package com.hisun.kugga.duke.league.api.league;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.league.api.dto.LeagueDTO;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import com.hisun.kugga.duke.league.api.dto.leaguemember.BonusUserDTO;
import com.hisun.kugga.duke.league.api.dto.task.LeagueRuleDTO;
import com.hisun.kugga.duke.league.convert.UserConvert;
import com.hisun.kugga.duke.league.dal.dataobject.BonusUserDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.dal.mysql.growthinfo.GrowthInfoMapper;
import com.hisun.kugga.duke.league.service.*;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zuocheng
 */
@Service
public class LeagueApiImpl implements LeagueApi {
    @Resource
    private LeagueService leagueService;

    @Resource
    private LeagueRuleService leagueRuleService;
    @Resource
    private LeagueJoinApprovalService approvalService;
    @Resource
    private LeagueMemberService leagueMemberService;
    @Resource
    private GrowthInfoMapper growthInfoMapper;
    @Resource
    private LeagueGrowthLevelService levelService;

    @Override
    public Boolean isLeagueMember(Long leagueId, Long... userIds) {
        return leagueService.isLeagueMember(leagueId, userIds);
    }

    @Override
    public Boolean isLeagueAdmin(Long leagueId, Long userId) {
        return leagueService.isLeagueAdmin(leagueId, userId);
    }

    @Override
    public LeagueRuleDTO queryRule(Long leagueId) {
        leagueRuleService.getLeagueRuleInfo(leagueId);
        return BeanUtil.copyProperties(leagueRuleService.getLeagueRuleInfo(leagueId), LeagueRuleDTO.class);
    }

    @Override
    public Long getApprovalCount() {
        return approvalService.getApprovalCount();
    }

    @Override
    public LeagueDTO queryLeagueById(Long leagueId) {
        LeagueDO leagueDO = leagueService.getById(leagueId);
        return BeanUtil.copyProperties(leagueDO, LeagueDTO.class);
    }

    @Override
    public List<Long> queryUserAllJoinLeagueId(Long userId) {
        return leagueService.queryUserAllJoinLeagueId(userId);
    }

    @Override
    public List<BonusUserDTO> canBonus(Long leagueId) {
        List<BonusUserDO> bonusUserDOList = leagueMemberService.canBonus(leagueId);
        return UserConvert.INSTANCE.convert(bonusUserDOList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void growthThenLevel(GrowthDTO growthDTO) {
        if (ObjectUtil.hasNull(growthDTO.getLeagueId(), growthDTO.getUserId(), growthDTO.getGrowthValue())) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.ILLEGAL_PARAM);
        }
        leagueMemberService.growthThenLevel(growthDTO);
        growthInfoMapper.save(growthDTO);
    }

    @Override
    public UserGrowthLevelDTO getUserGrowthInfo(Long leagueId, Long userId) {
        return levelService.getUserGrowthInfo(leagueId,userId);
    }
}
