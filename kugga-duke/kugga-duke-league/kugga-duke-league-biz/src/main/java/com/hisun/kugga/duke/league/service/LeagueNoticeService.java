package com.hisun.kugga.duke.league.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.league.api.dto.leaguenotice.LeagueNoticeDTO;
import com.hisun.kugga.duke.league.bo.task.TaskAcceptBO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.vo.notice.LeagueNoticeVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:43
 */
public interface LeagueNoticeService {

    void create(TaskInitBO vo);

    CommonResult accept(TaskAcceptBO vo);

    CommonResult finish(TaskFinishBO vo);

    /**
     * 根据公会ID查询公会公告栏详情
     */
    Page<LeagueNoticeVO> getAll(Long id);

    /**
     * 分页查询公会公告栏
     */
    Page<LeagueNoticeVO> getPage(Long id);

    /**
     * 保存公告栏消息
     * @param leagueNoticeDTO
     */
    void insert(LeagueNoticeDTO leagueNoticeDTO);
}
