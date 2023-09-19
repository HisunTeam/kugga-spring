package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.vo.task.TaskChatResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskChatVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:43
 */
public interface TaskChatService {

    CommonResult<TaskChatResultVO> judgeChatPay(TaskChatVO vo);
}
