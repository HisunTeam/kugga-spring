package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsLabelRelationDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 贴子与论坛标签关联表 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-08
 */
@Mapper
public interface PostsLabelRelationMapper extends BaseMapperX<PostsLabelRelationDO> {
    /**
     * 根据贴子ID删除贴子与标签关联关系
     * @param postsId
     * @return
     */
    default int deleteByPostsId(long postsId){
        return delete(new LambdaQueryWrapperX<PostsLabelRelationDO>().eq(PostsLabelRelationDO::getPostsId, postsId));
    }

    /**
     * 根据贴子ID删除贴子与标签关联关系
     * @param postsId
     * @return
     */
    default List<PostsLabelRelationDO> selectByPostsId(long postsId){
        return selectList(new LambdaQueryWrapperX<PostsLabelRelationDO>().eq(PostsLabelRelationDO::getPostsId, postsId));
    }
}
