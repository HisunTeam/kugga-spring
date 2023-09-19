package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.ForumDistrictDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 论坛分区表 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-10-17
 */
@Mapper
public interface ForumDistrictMapper extends BaseMapperX<ForumDistrictDO> {
    /**
     * 根据分区ID 与 论坛ID查询数据是否存在
     *
     * @param forumId
     * @return
     */
    default Long countById(long id, long forumId) {
        return selectCount(new LambdaQueryWrapperX<ForumDistrictDO>()
                .eq(ForumDistrictDO::getId, id)
                .eq(ForumDistrictDO::getForumId, forumId));
    }

    ;

    /**
     * 根据论坛ID查询,论坛的分区列表
     *
     * @param forumId
     * @return
     */
    default List<ForumDistrictDO> selectForumDistrictsByForumId(long forumId) {
        return selectList(new LambdaQueryWrapperX<ForumDistrictDO>().eq(ForumDistrictDO::getForumId, forumId));
    }

    /**
     * 根据论坛ID删除分区
     *
     * @param forumId
     * @return
     */
    default int deleteByForumId(long forumId) {
        return delete(new LambdaQueryWrapperX<ForumDistrictDO>().eq(ForumDistrictDO::getForumId, forumId));
    }
}
