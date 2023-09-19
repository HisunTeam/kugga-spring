package com.hisun.kugga.duke.league.service.impl;

import com.hisun.kugga.duke.chat.api.ChatApi;
import com.hisun.kugga.duke.chat.api.dto.ChatCheckReqDto;
import com.hisun.kugga.duke.chat.api.dto.ChatCheckRespDto;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueRuleDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleMapper;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.service.TaskChatService;
import com.hisun.kugga.duke.league.utils.TaskUtils;
import com.hisun.kugga.duke.league.vo.task.TaskChatResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskChatVO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.hisun.kugga.duke.league.constants.TaskConstants.*;
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
public class TaskChatServiceImpl implements TaskChatService {

    ChatApi chatApi;
    LeagueService leagueService;
    LeagueRuleMapper leagueRuleMapper;
    TaskUtils taskUtils;

    @Override
    public CommonResult<TaskChatResultVO> judgeChatPay(TaskChatVO vo) {
        if (Objects.isNull(vo.getByUserId())) {
            throw new ServiceException(BY_CHAT_USER_ID_NULL);
        }
        Long byLeagueId = vo.getByLeagueId();
        if (Objects.isNull(byLeagueId)) {
            throw new ServiceException(REQUEST_PARAMETERS_MISSING);
        }
        TaskChatResultVO result = new TaskChatResultVO();
        if (getLoginUserId().equals(vo.getByUserId())) {
            throw new ServiceException(NOT_ONESELF_CHAT);
        }
        if (!taskUtils.leagueAuthFlag(byLeagueId)) {
            throw new ServiceException(LEAGUE_NOT_AUTH_NO_CHAT);
        }
        LeagueRuleDO leagueRuleDO = leagueRuleMapper.selectById(byLeagueId);
//        log.error("byLeagueId：" + byLeagueId + "leagueRuleDO" + leagueRuleDO);
        if (Objects.isNull(leagueRuleDO)) {
            throw new ServiceException(LEAGUE_RULE_NO_EXIST);
        }

        /* 能不能聊天 */
        ChatCheckRespDto chat = chatApi.chatCheck(new ChatCheckReqDto().setUserId(getLoginUserId()).setReceiveUserId(vo.getByUserId()));
        if (chat.getIsOnChat()) {
            // 能聊天 直接返回
            result.setChatStatus(Boolean.TRUE);
            return success(result);
        }

        Boolean leagueMember = leagueService.isLeagueMember(byLeagueId, getLoginUserId(), vo.getByUserId());
        if (leagueMember) {
            // 在一个工会 也能聊天
            result.setChatStatus(Boolean.TRUE);
            return success(result);
        }

        // 其他情况不能聊天
        result.setChatStatus(Boolean.FALSE);
        result.setTaskChatParam2(chat);

        result.setPrice(leagueRuleDO.getChatPrice());
        return success(result);
    }
}
