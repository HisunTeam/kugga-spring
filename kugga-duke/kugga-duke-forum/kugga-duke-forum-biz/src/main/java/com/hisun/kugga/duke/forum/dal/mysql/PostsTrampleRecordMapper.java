package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsTrampleRecordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 贴子点踩记录 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-09
 */
@Mapper
public interface PostsTrampleRecordMapper extends BaseMapperX<PostsTrampleRecordDO> {
    /**
     * 根据用户ID与贴子ID删除点踩记录
     * @param userId
     * @param postsId
     * @return
     */
    default int deleteByUserAndPosts(long userId, long postsId){
        return delete(new LambdaQueryWrapperX<PostsTrampleRecordDO>()
                .eq(PostsTrampleRecordDO::getUserId, userId)
                .eq(PostsTrampleRecordDO::getPostsId, postsId));
    }

    /**
     * 根据用户ID与贴子ID删除点赞记录
     * @param userId
     * @param postsId
     * @return
     */
    default PostsTrampleRecordDO selectByUserAndPosts(long userId, long postsId){
        return selectOne(new LambdaQueryWrapperX<PostsTrampleRecordDO>()
                .eq(PostsTrampleRecordDO::getUserId,userId)
                .eq(PostsTrampleRecordDO::getPostsId,postsId)
        );
    }
}
