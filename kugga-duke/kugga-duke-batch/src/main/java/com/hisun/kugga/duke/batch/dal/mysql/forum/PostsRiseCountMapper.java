package com.hisun.kugga.duke.batch.dal.mysql.forum;

import com.hisun.kugga.duke.batch.dal.dataobject.forum.PostsRiseCountDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Post Rise Count Table Mapper Interface
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:33:27
 */
@Mapper
public interface PostsRiseCountMapper extends BaseMapperX<PostsRiseCountDO> {
    /**
     * Query the statistics of replies (popularity) within a time interval
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<PostsRiseCountDO> queryRiseNum(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}

