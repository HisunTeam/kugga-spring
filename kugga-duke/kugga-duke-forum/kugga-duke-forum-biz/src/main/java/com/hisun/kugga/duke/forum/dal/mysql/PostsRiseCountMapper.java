package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsRiseCountDO;
import com.hisun.kugga.duke.forum.vo.HotPostsVO;
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
     * 查询XXX时间内 热度上升最快的5个贴子 涉及条件 公会开通了申请加入 公会开通了热贴扫描 贴子开通了热贴扫描
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<HotPostsVO> riseHotPosts(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 查询XXX时间内 热度上升最快的贴子子,可排除不需要的贴,可自定义查询条数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<HotPostsVO> riseHotPostsComplex(@Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime,
                                         @Param("item") List<Long> item,
                                         @Param("number") Integer number);
}
