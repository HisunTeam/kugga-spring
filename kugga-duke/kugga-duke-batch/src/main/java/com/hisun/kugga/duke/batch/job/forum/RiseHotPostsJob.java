package com.hisun.kugga.duke.batch.job.forum;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.batch.dal.dataobject.forum.PostsRiseCountDO;
import com.hisun.kugga.duke.batch.dal.mysql.forum.PostsRiseCountBatchInsertMapper;
import com.hisun.kugga.duke.batch.dal.mysql.forum.PostsRiseCountMapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Rise Hot Posts Count Job
 * Counts the rising posts in a forum.
 * Author: zuo_cheng
 */
@Slf4j
@Component
public class RiseHotPostsJob implements JobHandler {

    @Resource
    private PostsRiseCountMapper postsRiseCountMapper;

    @Resource
    private PostsRiseCountBatchInsertMapper postsRiseCountBatchInsertMapper;

    @Override
    public String execute(String param) throws Exception {
        if (StrUtil.isBlank(param)) {
            throw exception(BusinessErrorCodeConstants.ILLEGAL_PARAMS);
        }
        // Count the data for the previous minute
        LocalDateTime nowTime = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime startTime = nowTime.minusMinutes(Long.valueOf(param));
        LocalDateTime entTime = nowTime.minusMinutes(1L).withSecond(59);
        // Query the rising post count within the specified time range
        List<PostsRiseCountDO> list = postsRiseCountMapper.queryRiseNum(startTime, entTime);
        log.info("Read data for discussions and replies that occurred between [{}] and [{}]. Found [{}] records (Note: Logging is done here as there has been no incoming data)", startTime, entTime, list.size());
        // Save the batch of rising post counts
        postsRiseCountBatchInsertMapper.saveBatch(list);
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
