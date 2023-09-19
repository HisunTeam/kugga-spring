package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsCommentTrampleRecordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 讨论点踩记录 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-09
 */
@Mapper
public interface PostsCommentTrampleRecordMapper extends BaseMapperX<PostsCommentTrampleRecordDO> {
    /**
     * 根据用户ID与楼层ID删除点踩记录
     * @param userId
     * @param commentId
     * @return
     */
    default int deleteByUserAndPosts(long userId, long commentId){
        return delete(new LambdaQueryWrapperX<PostsCommentTrampleRecordDO>()
                .eq(PostsCommentTrampleRecordDO::getUserId, userId)
                .eq(PostsCommentTrampleRecordDO::getCommentId, commentId));
    }
}
