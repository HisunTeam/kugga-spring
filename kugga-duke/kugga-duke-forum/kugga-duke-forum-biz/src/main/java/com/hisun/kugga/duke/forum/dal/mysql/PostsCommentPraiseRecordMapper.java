package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsCommentPraiseRecordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 讨论点赞记录 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-09
 */
@Mapper
public interface PostsCommentPraiseRecordMapper extends BaseMapperX<PostsCommentPraiseRecordDO> {
    /**
     * 根据用户ID与楼层ID删除点赞记录
     * @param userId
     * @param commentId
     * @return
     */
    default int deleteByUserAndPosts(long userId, long commentId){
        return delete(new LambdaQueryWrapperX<PostsCommentPraiseRecordDO>()
                .eq(PostsCommentPraiseRecordDO::getUserId,userId)
                .eq(PostsCommentPraiseRecordDO::getCommentId,commentId)
        );
    }
}
