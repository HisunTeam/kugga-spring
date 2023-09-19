package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsPraiseRecordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 贴子点赞记录 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-09
 */
@Mapper
public interface PostsPraiseRecordMapper extends BaseMapperX<PostsPraiseRecordDO> {
    /**
     * 根据用户ID与贴子ID删除点赞记录
     * @param userId
     * @param postsId
     * @return
     */
    default int deleteByUserAndPosts(long userId, long postsId){
        return delete(new LambdaQueryWrapperX<PostsPraiseRecordDO>()
                .eq(PostsPraiseRecordDO::getUserId,userId)
                .eq(PostsPraiseRecordDO::getPostsId,postsId)
        );
    }

    /**
     * 根据用户ID与贴子ID删除点赞记录
     * @param userId
     * @param postsId
     * @return
     */
    default PostsPraiseRecordDO selectByUserAndPosts(long userId, long postsId){
        return selectOne(new LambdaQueryWrapperX<PostsPraiseRecordDO>()
                .eq(PostsPraiseRecordDO::getUserId,userId)
                .eq(PostsPraiseRecordDO::getPostsId,postsId)
        );
    }
}
