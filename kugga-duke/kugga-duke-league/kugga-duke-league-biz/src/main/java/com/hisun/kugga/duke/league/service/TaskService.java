package com.hisun.kugga.duke.league.service;

import com.hisun.kugga.duke.league.bo.task.*;
import com.hisun.kugga.duke.league.vo.task.TaskCreateResultVO;
import com.hisun.kugga.duke.league.vo.task.TaskInitResultVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:43
 */
public interface TaskService {

    /**
     * 初始化一个任务
     *
     * @param bo
     * @return
     */
    TaskInitResultVO init(TaskInitBO bo);

    /**
     * 创建一个任务
     *
     * @param bo
     * @return
     */
    TaskCreateResultVO create(TaskCreateBO bo);

    /**
     * 接受一个任务
     *
     * @param bo
     * @return
     */
    CommonResult accept(TaskAcceptBO bo);

    /**
     * 完成一个任务
     *
     * @param bo
     * @return
     */
    TaskFinishResultBO finish(TaskFinishBO bo);
}
