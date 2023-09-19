package com.hisun.kugga.duke.user.service.favoritegroup;


import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupExportReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupRespVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupUpdateReqVO;

import javax.validation.Valid;

/**
 * 收藏分组 Service 接口
 *
 * @author 芋道源码
 */
public interface FavoriteGroupService {

    /**
     * 更新收藏分组
     *
     * @param updateReqVO 更新信息
     */
    void updateFavoriteGroup(@Valid FavoriteGroupUpdateReqVO updateReqVO);


    void deleteFavoriteGroup(Long id, String type);

    void validateFavoriteGroupExists(Long id, Long userId, String type);

    /**
     * 获得收藏分组列表
     *
     * @param exportReqVO 请求vo
     * @return 收藏分组列表
     */
    FavoriteGroupRespVO getFavoriteGroupList(FavoriteGroupExportReqVO exportReqVO);

}
