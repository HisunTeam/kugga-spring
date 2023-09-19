package com.hisun.kugga.duke.batch.dal.mysql.forum;

import com.hisun.kugga.duke.batch.dal.dataobject.forum.PostsRiseCountDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 贴子上升计数表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:33:27
 */
@Mapper
public interface PostsRiseCountMapper extends BaseMapperX<PostsRiseCountDO> {
    /**
     * 查询时间断内的回复（热度）统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<PostsRiseCountDO> queryRiseNum(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
