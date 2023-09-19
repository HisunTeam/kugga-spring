package com.hisun.kugga.duke.league.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueJoinApprovalDO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueRuleDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueJoinApprovalMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleTemplateMapper;
import com.hisun.kugga.duke.league.service.LeagueRuleService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.vo.rule.LeagueJoinRuleVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueRuleVO;
import com.hisun.kugga.duke.league.vo.rule.SubscribeSelectVo;
import com.hisun.kugga.duke.system.api.s3.S3FileUploadApi;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.duke.league.constants.TaskConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:44
 */
@Slf4j
@Service
@AllArgsConstructor
public class LeagueRuleServiceImpl implements LeagueRuleService {

    private static final String DEFAULT_SUBSCRIBE_SELECT = "1_0_0_0";

    LeagueRuleMapper leagueRuleMapper;
    LeagueRuleTemplateMapper leagueRuleTemplateMapper;
    LeagueMapper leagueMapper;
    LeagueJoinApprovalMapper approvalMapper;

    @Resource
    private LeagueService leagueService;

    @Resource
    private S3FileUploadApi s3FileUploadApi;

    /**
     * 根据公会ID查询公会规则
     *
     * @param id
     * @return
     */
    @Override
    public LeagueRuleVO get(Long id) {
        Long userId = getLoginUserId();
        if (!leagueService.isLeagueMember(id, userId)) {
            log.info("用户[{}]非公会[{}]管理员,无法查询", userId, id);
            throw new ServiceException(QUERY_NOTICE_NOT_IN_LEAGUE);
        }
        LeagueDO leagueDO = leagueMapper.selectById(id);
        LeagueRuleDO leagueRuleDO = leagueRuleMapper.selectOne("league_id", id);
        LeagueRuleVO rule = new LeagueRuleVO();
        BeanUtils.copyProperties(leagueDO, rule);
        BeanUtils.copyProperties(leagueRuleDO, rule);
        rule.setLeagueAuthFlag(leagueDO.getAuthFlag());
        return rule;
    }

    /**
     * 修改公会规则
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResult update(LeagueRuleVO vo) {
        //校验公会规则参数
        checkLeagueRule(vo);
        //检查当前用户是否为被修改公会的管理员 和 公会名称唯一
        checkAdminAndName(vo);

        Long userId = getLoginUserId();
        LeagueRuleDO leagueRuleDO = new LeagueRuleDO();
        BeanUtils.copyProperties(vo, leagueRuleDO);

        leagueRuleDO.setUpdateUserId(userId).setUpdateTime(LocalDateTime.now());
        leagueRuleMapper.updateById(leagueRuleDO);

        LeagueDO leagueDO = new LeagueDO().setId(vo.getLeagueId());
        BeanUtils.copyProperties(vo, leagueDO);
        leagueDO.setUpdater(userId.toString()).setUpdateTime(LocalDateTime.now());
        leagueMapper.updateById(leagueDO);
        return success();
    }


    @Override
    public String updateAvatar(Long id, byte[] bytes, String originalFilename, String contentType) {
        Long userId = getLoginUserId();
        //检查当前用户是否为被修改公会的管理员
        if (!leagueService.isLeagueAdmin(id, userId)) {
            log.info("用户[{}]非公会[{}]管理员,无法操作更新", userId, id);
            throw exception(BusinessErrorCodeConstants.LEAGUE_NOT_ADMIN);
        }
        String leagueAvatar = s3FileUploadApi.uploadLeagueAvatar(id, bytes, originalFilename, contentType);
        leagueMapper.updateById(LeagueDO.builder()
                .id(id)
                .leagueAvatar(leagueAvatar)
                .build()
        );
        return leagueAvatar;
    }

    @Override
    public LeagueRuleVO getLeagueRuleInfo(Long leagueId) {
        Optional.ofNullable(leagueId).orElseThrow(() -> new ServiceException(LEAGUE_ID_IS_NULL));

        LeagueRuleVO ruleVO = leagueRuleMapper.getLeagueRuleInfoByLeagueId2(leagueId);
        return ruleVO;
    }

    @Override
    public LeagueJoinRuleVO getLeagueJoinRule(Long leagueId) {
        LeagueRuleVO ruleVO = this.getLeagueRuleInfo(leagueId);
        LeagueJoinRuleVO leagueJoinRuleVO = new LeagueJoinRuleVO();
        if (ObjectUtil.isNotNull(ruleVO)) {
            BeanUtils.copyProperties(ruleVO, leagueJoinRuleVO);
        }
        // 根据str转化为具体状态
        SubscribeSelectVo subscribeSelectVo = new SubscribeSelectVo(leagueJoinRuleVO.getSubscribeSelect());
        leagueJoinRuleVO.setSubscribeSelectVo(subscribeSelectVo);
        return leagueJoinRuleVO;
    }

    /**
     * 校验公会规则参数
     *
     * @param vo
     */
    private void checkLeagueRule(LeagueRuleVO vo) {
        BigDecimal authPrice = vo.getAuthPrice();
        //必传参数不能为空
        if (Objects.isNull(vo.getEnabledAuth()) || Objects.isNull(vo.getReportPrice()) || Objects.isNull(vo.getChatPrice())) {
            ServiceException.throwServiceException(REQUIRED_PARAMETER_IS_EMPTY);
        }
        //公会认证价格为0 或 大于100且是100的整数倍
        //取反(0或大于等于1的正整数)
        if (vo.getEnabledAuth() && !(authPrice.doubleValue() == 0 || (authPrice.doubleValue() >= 1 && new BigDecimal(authPrice.intValue()).compareTo(authPrice) == 0))) {
            throw new ServiceException(REQUEST_PARAMETER_ILLEGAL, "authPrice");
        }
        //推荐报告价格大于等于1
        if (vo.getReportPrice().doubleValue() < 1) {
            throw new ServiceException(REQUEST_PARAMETER_ILLEGAL, "reportPrice");
        }
        //聊天价格大于等于1
        if (vo.getChatPrice().doubleValue() < 1) {
            throw new ServiceException(REQUEST_PARAMETER_ILLEGAL, "chatPrice");
        }
        //当有加入公会审批记录时，不能修改审批状态
        List<LeagueJoinApprovalDO> approvalDos = approvalMapper.selectApprovalByLeagueId(vo.getLeagueId(), LocalDateTime.now());
        LeagueRuleVO leagueRuleInfo = getLeagueRuleInfo(vo.getLeagueId());
        if (ObjectUtil.isNotEmpty(approvalDos) &&
                ObjectUtil.notEqual(leagueRuleInfo.getEnabledAdminApproval(), vo.getEnabledAdminApproval())) {
            throw exception(EXIST_JOIN_LEAGUE_RECORD_NOT_UPDATE_RULE);
        }
        //订阅选择长度校验
        if (ObjectUtil.notEqual(DEFAULT_SUBSCRIBE_SELECT.length(), vo.getSubscribeSelect().length())) {
            throw exception(PARAM_ERROR);
        }
    }

    /**
     * 检查当前用户是否为被修改公会的管理员 和 公会名称唯一
     *
     * @param vo
     */
    private void checkAdminAndName(LeagueRuleVO vo) {
        Long userId = getLoginUserId();
        //检查当前用户是否为被修改公会的管理员
        if (!leagueService.isLeagueAdmin(vo.getLeagueId(), userId)) {
            log.info("用户[{}]非公会[{}]管理员,无法操作更新", userId, vo.getLeagueId());
            throw exception(BusinessErrorCodeConstants.LEAGUE_NOT_ADMIN);
        }
        //公会名称唯一
        LambdaQueryWrapper<LeagueDO> wrapper = new LambdaQueryWrapper<LeagueDO>()
                .eq(LeagueDO::getLeagueName, vo.getLeagueName())
                .ne(LeagueDO::getId, vo.getLeagueId());
        Long leagueCount = leagueMapper.selectCount(wrapper);
        if (Objects.isNull(leagueCount) || leagueCount >= 1) {
            ServiceException.throwServiceException(GUILD_NAME_NOT_UNIQUE);
        }
    }
}
