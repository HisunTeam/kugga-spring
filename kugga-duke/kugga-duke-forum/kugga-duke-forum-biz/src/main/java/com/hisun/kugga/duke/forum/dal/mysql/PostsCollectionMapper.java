package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsCollectionDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 贴子收藏表 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-11
 */
@Mapper
public interface PostsCollectionMapper extends BaseMapperX<PostsCollectionDO> {
    /**
     * 删除用户收藏
     * @param userId
     * @param postsId
     * @return
     */
    default int deleteByUser(long userId, long postsId){
        return delete(new LambdaQueryWrapperX<PostsCollectionDO>()
                .eq(PostsCollectionDO::getPostsId, postsId)
                .eq(PostsCollectionDO::getUserId, userId));
    }

    default PostsCollectionDO selectByUser(long userId, long postsId){
        return selectOne(new LambdaQueryWrapperX<PostsCollectionDO>()
                .eq(PostsCollectionDO::getPostsId, postsId)
                .eq(PostsCollectionDO::getUserId, userId));
    }
}
