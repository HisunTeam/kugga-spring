package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorTrampleRecordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 楼层点踩记录 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-09
 */
@Mapper
public interface PostsFloorTrampleRecordMapper extends BaseMapperX<PostsFloorTrampleRecordDO> {
    /**
     * 根据用户ID与楼层ID删除点踩记录
     * @param userId
     * @param floorId
     * @return
     */
    default int deleteByUserAndPosts(long userId, long floorId){
        return delete(new LambdaQueryWrapperX<PostsFloorTrampleRecordDO>()
                .eq(PostsFloorTrampleRecordDO::getUserId, userId)
                .eq(PostsFloorTrampleRecordDO::getFloorId, floorId));
    }

    /**
     * 根据用户ID与楼层ID删除点赞记录
     * @param userId
     * @param floorId
     * @return
     */
    default PostsFloorTrampleRecordDO selectByUserAndPosts(long userId, long floorId){
        return selectOne(new LambdaQueryWrapperX<PostsFloorTrampleRecordDO>()
                .eq(PostsFloorTrampleRecordDO::getUserId,userId)
                .eq(PostsFloorTrampleRecordDO::getFloorId,floorId)
        );
    }
}
