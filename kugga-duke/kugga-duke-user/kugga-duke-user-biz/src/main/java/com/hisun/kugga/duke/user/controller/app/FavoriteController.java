package com.hisun.kugga.duke.user.controller.app;

import com.hisun.kugga.duke.user.common.FavoriteEnum;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteDeleteReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoritePageReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.service.favorite.FavoriteService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Api(tags = "A4-用户收藏")
@RestController
@RequestMapping("/user/favorite")
@Validated
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @PostMapping("/create")
    @ApiOperation("a.添加收藏")
    public CommonResult<Long> createFavorite(@Valid @RequestBody FavoriteCreateReqVO createReqVO) {
        createReqVO.setUserId(getLoginUserId());
        return success(favoriteService.createFavorite(createReqVO));
    }

    @DeleteMapping("/delete")
    @ApiOperation("b.取消收藏")
    public CommonResult<Boolean> deleteFavorite(@Valid @RequestBody FavoriteDeleteReqVO reqVO) {
        reqVO.setUserId(getLoginUserId());
        favoriteService.deleteFavorite(reqVO);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("d.收藏分页")
    public CommonResult<PageResult<FavoriteRespVO>> getFavoritePage(@Valid FavoritePageReqVO pageVO) {
        pageVO.setUserId(getLoginUserId());
        PageResult<FavoriteRespVO> pageResult = favoriteService.getFavoritePage(pageVO);
        return success(pageResult);
    }

    @GetMapping("/getAuthLeagues")
    @ApiOperation("e.获取可认证的公会")
    public CommonResult<PageResult<FavoriteRespVO>> getAuthLeagues(@Valid FavoritePageReqVO pageVO) {
        pageVO.setUserId(getLoginUserId());
        pageVO.setType(FavoriteEnum.LEAGUE.getCode());
        PageResult<FavoriteRespVO> pageResult = favoriteService.getAuthLeagues(pageVO);
        return success(pageResult);
    }
}
