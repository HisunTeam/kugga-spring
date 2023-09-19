package com.hisun.kugga.duke.forum.dal.mysql;


import com.hisun.kugga.duke.forum.dal.dataobject.PostsFloorContentDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 贴子内容表 Mapper 接口
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:32:47
 */
@Mapper
public interface PostsFloorContentMapper extends BaseMapperX<PostsFloorContentDO> {
    /**
     * 根据posts_id查询贴子内容
     *
     * @param floorId
     * @return
     */
    default List<PostsFloorContentDO> selectByFloorId(Long floorId) {
        return selectList(new LambdaQueryWrapperX<PostsFloorContentDO>()
                .eq(PostsFloorContentDO::getFloorId, floorId)
                .orderByAsc(PostsFloorContentDO::getId)
        );
    }

    /**
     * 根据floorId,删除内容
     *
     * @param floorId
     * @return
     */
    default int deleteByFloorIdId(Long floorId) {
        return delete(new LambdaQueryWrapperX<PostsFloorContentDO>()
                .eq(PostsFloorContentDO::getFloorId, floorId)
        );
    }


}
