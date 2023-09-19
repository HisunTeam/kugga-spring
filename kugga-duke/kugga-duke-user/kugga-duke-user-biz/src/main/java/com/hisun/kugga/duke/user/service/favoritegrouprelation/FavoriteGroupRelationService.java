package com.hisun.kugga.duke.user.service.favoritegrouprelation;

import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.DeleteRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.FavoriteRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.GroupRelationUpdateReqVO;

import javax.validation.Valid;


/**
 * 收藏分组关联 Service 接口
 *
 * @author 芋道源码
 */
public interface FavoriteGroupRelationService {



    /**
     * 更新收藏分组关联
     *
     * @param updateReqVO 更新信息
     */
    void updateFavoriteRelation(@Valid FavoriteRelationUpdateReqVO updateReqVO);

    /**
     * 更新收藏分组关联
     *
     * @param updateReqVO 更新信息
     */
    void updateGroupRelation(@Valid GroupRelationUpdateReqVO updateReqVO);

    /**
     * 删除收藏分组关联
     *
     * @param updateReqVO 删除信息
     */
    void deleteFavoriteRelation(@Valid DeleteRelationUpdateReqVO updateReqVO);

    /**
     * 删除收藏的分组关联
     * @param favoriteId
     * @param type
     */
    void deleteFavoriteRelation(Long favoriteId, String type);
}
