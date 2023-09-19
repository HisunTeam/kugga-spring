package com.hisun.kugga.duke.user.api.oauth2;

import com.hisun.kugga.duke.user.api.oauth2.dto.GroupRelationUpdateReqDTO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.GroupRelationUpdateReqVO;
import com.hisun.kugga.duke.user.service.favoritegrouprelation.FavoriteGroupRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FavoriteGroupApiImpl implements FavoriteGroupApi{
    @Resource
    private FavoriteGroupRelationService favoriteGroupRelationService;
    @Override
    public void deleteFavoriteRelation(Long favoriteId, String type) {
        favoriteGroupRelationService.deleteFavoriteRelation(favoriteId,type);
    }

    @Override
    public void updateFavoriteGroupRelation(GroupRelationUpdateReqDTO reqDTO) {
        GroupRelationUpdateReqVO reqVO = new GroupRelationUpdateReqVO()
                .setFavoriteId(reqDTO.getFavoriteId())
                .setGroupIds(reqDTO.getGroupIds())
                .setContentId(reqDTO.getContentId())
                .setFavoriteLeagueId(reqDTO.getFavoriteLeagueId())
                .setType(reqDTO.getType());

        favoriteGroupRelationService.updateGroupRelation(reqVO);
    }
}
