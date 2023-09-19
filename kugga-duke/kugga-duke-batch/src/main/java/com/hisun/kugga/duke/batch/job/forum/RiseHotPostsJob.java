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
 * 上升热贴计数
 *
 * @author: zuo_cheng
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
        //统计上一分钟的数据
        LocalDateTime nowTime = LocalDateTime.now().withSecond(0).withNano(0);
        LocalDateTime startTime = nowTime.minusMinutes(Long.valueOf(param));
        LocalDateTime entTime = nowTime.minusMinutes(1L).withSecond(59);
        //查询指定时间内的上升量
        List<PostsRiseCountDO> list = postsRiseCountMapper.queryRiseNum(startTime, entTime);
        log.info("读取[{}]-[{}]时间内,发生回贴,讨论的数据[{}]条(PS:轻轻的打个日志,因为一直没有数据进来)", startTime, entTime, list.size());
        postsRiseCountBatchInsertMapper.saveBatch(list);
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }
}
