package com.hisun.kugga.duke.batch.service.log;

import com.hisun.kugga.duke.batch.dal.dataobject.log.JobLogDO;
import com.hisun.kugga.duke.batch.dal.mysql.log.JobLogMapper;
import com.hisun.kugga.duke.batch.enums.JobLogStatusEnum;
import com.hisun.kugga.framework.quartz.core.service.JobLogFrameworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Job 日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class JobLogServiceImpl implements JobLogFrameworkService {

    @Resource
    private JobLogMapper jobLogMapper;

    @Override
    public Long createJobLog(Long jobId, Date beginTime, String jobHandlerName, String jobHandlerParam, Integer executeIndex) {
        JobLogDO log = JobLogDO.builder().jobId(jobId).handlerName(jobHandlerName).handlerParam(jobHandlerParam).executeIndex(executeIndex)
                .beginTime(beginTime).status(JobLogStatusEnum.RUNNING.getStatus()).build();
        jobLogMapper.insert(log);
        return log.getId();
    }

    @Override
    @Async
    public void updateJobLogResultAsync(Long logId, Date endTime, Integer duration, boolean success, String result) {
        try {
            JobLogDO updateObj = JobLogDO.builder().id(logId).endTime(endTime).duration(duration)
                    .status(success ? JobLogStatusEnum.SUCCESS.getStatus() : JobLogStatusEnum.FAILURE.getStatus()).result(result).build();
            jobLogMapper.updateById(updateObj);
        } catch (Exception ex) {
            log.error("[updateJobLogResultAsync][logId({}) endTime({}) duration({}) success({}) result({})]",
                    logId, endTime, duration, success, result);
        }
    }

}
