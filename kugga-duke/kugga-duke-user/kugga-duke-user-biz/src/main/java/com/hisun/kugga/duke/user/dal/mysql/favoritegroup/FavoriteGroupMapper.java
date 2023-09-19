package com.hisun.kugga.duke.user.dal.mysql.favoritegroup;


import com.hisun.kugga.duke.user.dal.dataobject.favoritegroup.FavoriteGroupDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 收藏分组 Mapper
 *
 * @author lzt
 */
@Mapper
public interface FavoriteGroupMapper extends BaseMapperX<FavoriteGroupDO> {

    /**
     * 根据用户ID查询,用户的分组列表
     *
     * @param userId
     * @param type
     * @return
     */
    default List<FavoriteGroupDO> selectGroupByUserId(long userId, String type) {
        return selectList(new LambdaQueryWrapperX<FavoriteGroupDO>()
                .eq(FavoriteGroupDO::getUserId, userId)
                .eq(FavoriteGroupDO::getType, type)
                .orderByDesc(FavoriteGroupDO::getId));
    }

    /**
     * 根据用户ID删除分组
     *
     * @param userId
     * @param type
     * @return
     */
    default int deleteByUserId(long userId, String type) {
        return delete(new LambdaQueryWrapperX<FavoriteGroupDO>()
                .eq(FavoriteGroupDO::getUserId, userId)
                .eq(FavoriteGroupDO::getType, type));
    }

    /**
     * 根据用户ID 与 ID查询数据是否存在
     *
     * @param id
     * @param userId
     * @return
     */
    default Long countById(long id, long userId,String type) {
        return selectCount(new LambdaQueryWrapperX<FavoriteGroupDO>()
                .eq(FavoriteGroupDO::getId, id)
                .eq(FavoriteGroupDO::getType, type)
                .eq(FavoriteGroupDO::getUserId, userId));
    }
    /**
     * 根据用户ID 与 ID查询数据是否存在
     *
     * @param type
     * @param userId
     * @return
     */
    default Long countByIdType(Long userId,String type) {
        return selectCount(new LambdaQueryWrapperX<FavoriteGroupDO>()
                .eq(FavoriteGroupDO::getType, type)
                .eq(FavoriteGroupDO::getUserId, userId));
    }

    /**
     * 根据用户ID 修改分组信息
     *
     * @param favoriteGroupDO 收藏分组
     * @return int
     */
    int updateGroupById(@Param("favoriteGroupDO") FavoriteGroupDO favoriteGroupDO);

    /**
     * 根据用户ID 修改分组信息
     *
     * @param favoriteGroupDO 收藏分组
     * @return int
     */
    int countByIdTypeUserId(@Param("id") Long id, @Param("userId") Long userId, @Param("type") String type);



}
