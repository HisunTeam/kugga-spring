package com.hisun.kugga.duke.user.controller.app.favoritegrouprelation;


import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.DeleteRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.FavoriteRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.GroupRelationUpdateReqVO;
import com.hisun.kugga.duke.user.service.favoritegrouprelation.FavoriteGroupRelationService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

@Api(tags = "group-收藏分组关联")
@RestController
@RequestMapping("/duke/favorite-group-relation")
@Validated
public class FavoriteGroupRelationController {

    @Resource
    private FavoriteGroupRelationService favoriteGroupRelationService;

//    @PostMapping("/create")
//    @ApiOperation("创建收藏分组关联")
//    public CommonResult<Integer> createFavoriteGroupRelation(@Valid @RequestBody FavoriteGroupRelationCreateReqVO createReqVO) {
//        return success(favoriteGroupRelationService.createFavoriteGroupRelation(createReqVO));
//    }

    @PostMapping("/updateFavorites")
    @ApiOperation("更新收藏关联")
    public CommonResult<Boolean> updateFavoriteGroupRelation(@Valid @RequestBody FavoriteRelationUpdateReqVO updateReqVO) {
        favoriteGroupRelationService.updateFavoriteRelation(updateReqVO);
        return success(true);
    }
    @PostMapping("/deleteRelation")
    @ApiOperation("删除收藏关联")
    public CommonResult<Boolean> deleteFavoriteGroupRelation(@Valid @RequestBody DeleteRelationUpdateReqVO updateReqVO) {
        favoriteGroupRelationService.deleteFavoriteRelation(updateReqVO);
        return success(true);
    }

    @PostMapping("/updateGroups")
    @ApiOperation("更新分组关联")
    public CommonResult<Boolean> updateFavoriteGroupRelation(@Valid @RequestBody GroupRelationUpdateReqVO updateReqVO) {
        favoriteGroupRelationService.updateGroupRelation(updateReqVO);
        return success(true);
    }


}
