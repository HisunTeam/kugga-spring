package com.hisun.kugga.duke.user.dal.mysql.favoritegrouprelation;


import com.hisun.kugga.duke.user.dal.dataobject.favoritegrouprelation.FavoriteGroupRelationDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收藏分组关联 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FavoriteGroupRelationMapper extends BaseMapperX<FavoriteGroupRelationDO> {

    /**
     * 根据分组ID删除
     *
     * @param groupId
     * @return
     */
    default int deleteByGroupId(Long groupId, String type) {
        return delete(new LambdaQueryWrapperX<FavoriteGroupRelationDO>()
                .eq(FavoriteGroupRelationDO::getType, type)
                .eq(FavoriteGroupRelationDO::getGroupId, groupId));
    }

    /**
     * 更新
     *
     * @param groupId
     * @return
     */
    default int updateByGroupIdFavoriteId(FavoriteGroupRelationDO relationDO,Long oldGroupId) {
        return update(relationDO,new LambdaQueryWrapperX<FavoriteGroupRelationDO>()
                .eq(FavoriteGroupRelationDO::getFavoriteId, relationDO.getFavoriteId())
                .eq(FavoriteGroupRelationDO::getType, relationDO.getType())
                .eq(FavoriteGroupRelationDO::getGroupId, oldGroupId));
    }

    /**
     * 根据收藏ID删除
     *
     * @param favoriteId
     * @return
     */
    default int deleteByFavoriteId(Long favoriteId, String type) {
        return delete(new LambdaQueryWrapperX<FavoriteGroupRelationDO>()
                .eq(FavoriteGroupRelationDO::getType, type)
                .eq(FavoriteGroupRelationDO::getFavoriteId, favoriteId));
    }


    default int deleteByFavoriteIdGroupId(Long favoriteId, Long groupId, String type) {
        return delete(new LambdaQueryWrapperX<FavoriteGroupRelationDO>()
                .eq(FavoriteGroupRelationDO::getGroupId, groupId)
                .eq(FavoriteGroupRelationDO::getType, type)
                .eq(FavoriteGroupRelationDO::getFavoriteId, favoriteId));

    }

    /**
     * 查询是否已经存在分组
     * @param favoriteId
     * @param groupId
     * @param type
     * @return
     */
    default List<FavoriteGroupRelationDO> selectByFavoriteIdGroupId(Long favoriteId, Long groupId, String type) {
        return selectList(new LambdaQueryWrapperX<FavoriteGroupRelationDO>()
                .eq(FavoriteGroupRelationDO::getGroupId, groupId)
                .eq(FavoriteGroupRelationDO::getType, type)
                .eq(FavoriteGroupRelationDO::getFavoriteId, favoriteId));

    }

}
