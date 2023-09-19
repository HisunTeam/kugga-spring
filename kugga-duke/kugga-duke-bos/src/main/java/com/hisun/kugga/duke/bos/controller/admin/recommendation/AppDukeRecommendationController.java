package com.hisun.kugga.duke.bos.controller.admin.recommendation;

import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationReqVO;
import com.hisun.kugga.duke.bos.controller.admin.recommendation.vo.RecommendationRespVO;
import com.hisun.kugga.duke.bos.service.recommendation.DukeRecommendationService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "1管理后台 - 推荐报告管理")
@RestController
@RequestMapping("/duke/recommendation")
@Validated
public class AppDukeRecommendationController {

    @Resource
    private DukeRecommendationService recommendationService;

    //    pageLeagueDisplay
    @PostMapping("/pageRecommendation")
    @ApiOperation("查询公会列表")
    @PreAuthorize("@ss.hasPermission('duke:recommendation:select')")
    public CommonResult<PageResult<RecommendationRespVO>> pageLeagueDisplay(@Valid @RequestBody RecommendationReqVO reqVO) {
        return success(recommendationService.pageRecommendation(reqVO));
    }
}
