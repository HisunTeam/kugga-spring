package com.hisun.kugga.duke.league.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.api.dto.leaguenotice.LeagueNoticeDTO;
import com.hisun.kugga.duke.league.bo.task.TaskAcceptBO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueNoticeMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueRuleTemplateMapper;
import com.hisun.kugga.duke.league.factory.LeagueNoticeFactory;
import com.hisun.kugga.duke.league.handler.notice.AbstractLeagueNoticeHandler;
import com.hisun.kugga.duke.league.service.LeagueNoticeService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.vo.notice.LeagueNoticePageReqVO;
import com.hisun.kugga.duke.league.vo.notice.LeagueNoticeVO;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.hisun.kugga.duke.league.constants.TaskConstants.QUERY_NOTICE_NOT_IN_LEAGUE;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:44
 */
@Slf4j
@Service
@AllArgsConstructor
public class LeagueNoticeServiceImpl implements LeagueNoticeService {

    LeagueRuleMapper leagueRuleMapper;
    LeagueRuleTemplateMapper leagueRuleTemplateMapper;
    LeagueNoticeMapper leagueNoticeMapper;
    LeagueService leagueService;

    /**
     * @param
     */
    @Override
    public void create(TaskInitBO vo) {
        AbstractLeagueNoticeHandler build = LeagueNoticeFactory.build(vo.getLeagueNoticeType().getValue());
        build.create(vo);
    }

    @Override
    public CommonResult accept(TaskAcceptBO bo) {
        AbstractLeagueNoticeHandler build = LeagueNoticeFactory.build(bo.getLeagueNoticeType().getValue());
        return build.accept(bo);
    }

    @Override
    public CommonResult finish(TaskFinishBO bo) {
        AbstractLeagueNoticeHandler build = LeagueNoticeFactory.build(bo.getLeagueNoticeType().getValue());
        return build.finish(bo);
    }

    @Override
    public Page<LeagueNoticeVO> getAll(Long id) {
        if (!leagueService.isLeagueMember(id, getLoginUserId())) {
            throw new ServiceException(QUERY_NOTICE_NOT_IN_LEAGUE);
        }
        Page<LeagueNoticeVO> page = new Page<>(LeagueNoticePageReqVO.PAGE_NO_MAX, LeagueNoticePageReqVO.PAGE_SIZE_MAX);
        return leagueNoticeMapper.selectPageByLeagueId(page, id);
    }

    @Override
    public Page<LeagueNoticeVO> getPage(Long id) {
        if (!leagueService.isLeagueMember(id, getLoginUserId())) {
            throw new ServiceException(QUERY_NOTICE_NOT_IN_LEAGUE);
        }
        Page<LeagueNoticeVO> page = new Page<>(LeagueNoticePageReqVO.PAGE_NO_PAGE, LeagueNoticePageReqVO.PAGE_SIZE_PAGE);
        return leagueNoticeMapper.selectPageByLeagueId(page, id);
    }

    @Override
    public void insert(LeagueNoticeDTO leagueNoticeDTO) {
        LeagueNoticeDO leagueNoticeDO = new LeagueNoticeDO();
        BeanUtils.copyProperties(leagueNoticeDTO, leagueNoticeDO);
        leagueNoticeMapper.insertSmart(leagueNoticeDO);
    }
}
