package com.hisun.kugga.duke.forum.dal.mysql;

import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorPraiseRecordDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 楼层点赞记录 Mapper 接口
 * </p>
 *
 * @author zuoCheng
 * @since 2022-11-09
 */
@Mapper
public interface PostsFloorPraiseRecordMapper extends BaseMapperX<PostsFloorPraiseRecordDO> {
    /**
     * 根据用户ID与楼层ID删除点赞记录
     * @param userId
     * @param floorId
     * @return
     */
    default int deleteByUserAndPosts(long userId, long floorId){
        return delete(new LambdaQueryWrapperX<PostsFloorPraiseRecordDO>()
                .eq(PostsFloorPraiseRecordDO::getUserId,userId)
                .eq(PostsFloorPraiseRecordDO::getFloorId,floorId)
        );
    }

    /**
     * 根据用户ID与楼层ID删除点赞记录
     * @param userId
     * @param floorId
     * @return
     */
    default PostsFloorPraiseRecordDO selectByUserAndPosts(long userId, long floorId){
        return selectOne(new LambdaQueryWrapperX<PostsFloorPraiseRecordDO>()
                .eq(PostsFloorPraiseRecordDO::getUserId,userId)
                .eq(PostsFloorPraiseRecordDO::getFloorId,floorId)
        );
    }
}
