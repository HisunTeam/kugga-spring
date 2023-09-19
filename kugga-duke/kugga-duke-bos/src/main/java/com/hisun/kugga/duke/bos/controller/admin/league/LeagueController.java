package com.hisun.kugga.duke.bos.controller.admin.league;

import com.hisun.kugga.duke.bos.controller.admin.league.vo.*;
import com.hisun.kugga.duke.bos.service.league.LeagueService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "2管理后台 - 首页公会管理")
@RestController
@RequestMapping("/duke/league")
@Validated
public class LeagueController {

    @Resource
    private LeagueService leagueService;


    @PostMapping("/update")
    @ApiOperation("更新公会排序")
//    @PreAuthorize("@ss.hasPermission('duke:league:update')")
    public CommonResult<Boolean> updateLeague(@Valid @RequestBody LeagueRecommendsUpdateVO updateReqVO) {
        leagueService.updateLeague(updateReqVO);
        return success(true);
    }

    @PostMapping("/updateLeagueLabel")
    @ApiOperation("更新公会标签")
//    @PreAuthorize("@ss.hasPermission('duke:league:update')")
    public CommonResult<Boolean> updateLeagueLabel(@Valid @RequestBody LeagueRecommendsUpdateVO updateReqVO) {
        leagueService.updateLeagueLabel(updateReqVO);
        return success(true);
    }

    @PostMapping("/updateLeagueBatch")
    @ApiOperation("更新公会")
//    @PreAuthorize("@ss.hasPermission('duke:league:update')")
    public CommonResult<Boolean> updateLeagueBatch(@Valid @RequestBody LeagueUpdateBatchVO updateReqVO) {
        leagueService.updateLeagueBatch(updateReqVO);
        return success(true);
    }

    @PostMapping("/updateLeagueLabelBatch")
    @ApiOperation("更新公会")
//    @PreAuthorize("@ss.hasPermission('duke:league:update')")
    public CommonResult<Boolean> updateLeagueLabelBatch(@Valid @RequestBody LeagueUpdateBatchVO updateReqVO) {
        leagueService.updateLeagueLabelBatch(updateReqVO);
        return success(true);
    }

    @PostMapping("/page")
//    @PreAuthorize("@ss.hasPermission('duke:league:query')")
    @ApiOperation("查询推荐公会列表")
    public CommonResult<PageResult<LeagueRecommendsVO>> recommendLeagues(@Valid @RequestBody LeagueRecommendsReqVO reqVO) {
        return success(leagueService.recommendLeagues(reqVO));
    }

    @PostMapping("/pageLeagueDisplay")
//    @PreAuthorize("@ss.hasPermission('duke:league:query')")
    @ApiOperation("查询公会列表")
    public CommonResult<PageResult<LeagueRecommendsVO>> pageLeagueDisplay(@Valid @RequestBody LeagueRecommendsReqVO reqVO) {
        return success(leagueService.pageLeagueDisplay(reqVO));
    }

    @GetMapping("/getLeagueLabels")
//    @PreAuthorize("@ss.hasPermission('duke:league:query')")
    @ApiOperation("查询公会标签类型")
    public CommonResult<List<LeagueLabelVO>> getLeagueLabels() {
        return success(leagueService.getLeagueLabels());
    }
//
//    @GetMapping("/export-excel")
//    @ApiOperation("导出公会 Excel")
//    @PreAuthorize("@ss.hasPermission('duke:league:export')")
//    @OperateLog(type = EXPORT)
//    public void exportLeagueExcel(@Valid LeagueExportReqVO exportReqVO,
//              HttpServletResponse response) throws IOException {
//        List<LeagueDO> list = leagueService.getLeagueList(exportReqVO);
//        // 导出 Excel
//        List<LeagueExcelVO> datas = LeagueConvert.INSTANCE.convertList02(list);
//        ExcelUtils.write(response, "公会.xls", "数据", LeagueExcelVO.class, datas);
//    }

}
