package com.hisun.kugga.duke.league.handler.notice;

import com.hisun.kugga.duke.league.bo.task.TaskAcceptBO;
import com.hisun.kugga.duke.league.bo.task.TaskFinishBO;
import com.hisun.kugga.duke.league.bo.task.TaskInitBO;
import com.hisun.kugga.duke.league.constants.TaskConstants;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:26
 * @Vesion: 2.0
 * 策略模式+工厂模式+模板模式
 */
public abstract class AbstractLeagueNoticeHandler implements InitializingBean {

    /**
     * 创建公告内容 录入数据库
     *
     * @param bo
     * @return
     */
    abstract public void create(TaskInitBO bo);

    /**
     * @param bo
     * @return
     */
    public CommonResult accept(TaskAcceptBO bo) {
        throw new ServiceException(TaskConstants.NOT_EXPECT_METHOD);
    }

    /**
     * 完成公会公告
     *
     * @param bo
     * @return
     */
    abstract public CommonResult finish(TaskFinishBO bo);
}
