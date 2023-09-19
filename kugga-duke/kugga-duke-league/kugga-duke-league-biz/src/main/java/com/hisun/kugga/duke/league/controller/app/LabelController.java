package com.hisun.kugga.duke.league.controller.app;

import com.hisun.kugga.duke.league.service.LabelService;
import com.hisun.kugga.duke.league.vo.*;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author 泡面3分钟
 */
@Api(tags = "标签公会 APP")
@RestController
@RequestMapping("/league/label")
@Validated
@Slf4j
public class LabelController {
    @Resource
    private LabelService labelService;

    @GetMapping("/simple-labels")
    @ApiOperation("查询标签列表(只返回值 与 名称)")
    public CommonResult<List<SimpleLabelsVO>> simpleLabels() {
        return success(labelService.simpleLabels());
    }

    @GetMapping("/best-leagues")
    @ApiOperation("查询标签推荐的公会 及 标签信息")
    public CommonResult<List<LabelBestLeaguesVO>> labelBestLeagues() {
        return success(labelService.labelBestLeagues());
    }

    @GetMapping("/single-label")
    @ApiOperation("查询单个标签详情")
    @ApiImplicitParam(name = "labelValue", value = "标签值", required = true, example = "1", dataTypeClass = String.class)
    public CommonResult<LeagueLabelVO> singleLabel(@RequestParam(value = "labelValue") String labelValue) {
        return success(labelService.singleLabel(labelValue));
    }

    @PostMapping("/label-leagues")
    @ApiOperation("分页查询标签公会列表")
    public CommonResult<PageResult<LeagueVO>> labelLeagues(@Valid @RequestBody PageLabelLeaguesReqVO reqVO) {
        return success(labelService.labelLeagues(reqVO));
    }

}
