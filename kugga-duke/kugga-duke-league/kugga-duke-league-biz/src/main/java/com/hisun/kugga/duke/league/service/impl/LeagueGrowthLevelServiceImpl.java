package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueGrowthLevelDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueSubscribeDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueGrowthLevelMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueSubscribeMapper;
import com.hisun.kugga.duke.league.service.LeagueGrowthLevelService;
import com.hisun.kugga.duke.league.service.LeagueMemberService;
import com.hisun.kugga.duke.league.vo.rule.LeagueLevelReqVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueLevelRespVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueUserLevelRespVO;
import com.hisun.kugga.duke.league.vo.rule.LevelVo;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.ADMIN_AUTH;
import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.PARAM_ERROR;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/13 17:18
 */
@Service
public class LeagueGrowthLevelServiceImpl implements LeagueGrowthLevelService {
    @Resource
    private LeagueMemberService memberService;
    @Resource
    private LeagueGrowthLevelMapper levelMapper;
    @Resource
    private LeagueSubscribeMapper leagueSubscribeMapper;



    @Override
    public List<LeagueLevelRespVO> getLeagueLevel(LeagueLevelReqVO reqVO) {
        return levelMapper.selectLevelByLeagueId(reqVO.getLeagueId());
    }


    @Override
    public void updateLeagueLevelName(LeagueLevelReqVO updateVo) {
        preCheck(updateVo.getLeagueId());
        if (ObjectUtil.isEmpty(updateVo.getLevels())) {
            return;
        }
        for (LevelVo level : updateVo.getLevels()) {
            LeagueGrowthLevelDO levelDO = new LeagueGrowthLevelDO();
            BeanUtils.copyProperties(level, levelDO);
            levelMapper.updateById(levelDO);
        }
    }

    @Override
    public UserGrowthLevelDTO getUserGrowthInfo(Long leagueId, Long userId) {
        return levelMapper.getUserGrowthInfoByLeagueIdAndUserId(leagueId, userId);
    }

    @Override
    public LeagueUserLevelRespVO getUserLeagueGrowthInfos(Long leagueId, Long userId) {
        LeagueUserLevelRespVO respVO = levelMapper.getUserLeagueGrowthInfos(leagueId, userId);
        LeagueSubscribeDO subscribeDO = leagueSubscribeMapper.getByLeagueIdAndUserId(leagueId, userId);
        if (ObjectUtil.isNotNull(subscribeDO)){
            respVO.setExpireTime(subscribeDO.getExpireTime());
        }
        return respVO;
    }

    /**
     * 公会id非空、管理员校验
     *
     * @param leagueId
     */
    private void preCheck(Long leagueId) {
        if (ObjectUtil.isNull(leagueId)) {
            throw exception(PARAM_ERROR);
        }
        if (!memberService.isAdmin(getLoginUserId(), leagueId)) {
            throw exception(ADMIN_AUTH);
        }
    }
}
