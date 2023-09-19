package com.hisun.kugga.duke.user.api.oauth2;

import com.hisun.kugga.duke.user.api.oauth2.dto.GroupRelationUpdateReqDTO;

public interface FavoriteGroupApi {
    /**
     * 删除收藏关系
     * @param favoriteId
     * @param type
     */
    void deleteFavoriteRelation(Long favoriteId, String type);

    /**
     * 收藏分组
     * @param updateReqVO
     */
    void updateFavoriteGroupRelation(GroupRelationUpdateReqDTO updateReqVO);
}
