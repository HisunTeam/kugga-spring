package com.hisun.kugga.duke.user.controller.app.favoritegroup;

import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupExportReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupRespVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupUpdateReqVO;
import com.hisun.kugga.duke.user.service.favoritegroup.FavoriteGroupService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "group-收藏分组")
@RestController
@RequestMapping("/duke/favorite-group")
@Validated
public class FavoriteGroupController {

    @Resource
    private FavoriteGroupService favoriteGroupService;

    @PostMapping("/getGroups")
    @ApiOperation("查询收藏分组")
    public CommonResult<FavoriteGroupRespVO> createFavoriteGroup(@Valid @RequestBody FavoriteGroupExportReqVO reqVO) {
        return success(favoriteGroupService.getFavoriteGroupList(reqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新收藏分组")
    public CommonResult<Boolean> updateFavoriteGroup(@Valid @RequestBody FavoriteGroupUpdateReqVO updateReqVO) {
        favoriteGroupService.updateFavoriteGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除收藏分组")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteFavoriteGroup(@RequestParam("id") Long id,@RequestParam("type") String type) {
        favoriteGroupService.deleteFavoriteGroup(id,type);
        return success(true);
    }


}
