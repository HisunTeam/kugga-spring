package com.hisun.kugga.duke.forum.controller.app;

import com.hisun.kugga.duke.forum.service.DistrictService;
import com.hisun.kugga.duke.forum.vo.DistrictsReqVO;
import com.hisun.kugga.duke.forum.vo.DistrictsRespVO;
import com.hisun.kugga.duke.forum.vo.UpdateDistrictReqVO;
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

/**
 * @author zuocheng
 */
@Api(tags = "论坛分区 APP")
@RestController
@RequestMapping("/forum/district")
@Validated
public class DistrictController {

    @Resource
    private DistrictService districtService;

    /**
     * 创建分区
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/update-district")
    @ApiOperation("新增、删除分区")
    public CommonResult<Boolean> updateDistrict(@Valid @RequestBody UpdateDistrictReqVO reqVO) {
        districtService.updateDistrict(reqVO);
        return success(true);
    }

    /**
     * 查询分区列表
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/districts")
    @ApiOperation("查询分区列表")
    public CommonResult<DistrictsRespVO> districts(@Valid @RequestBody DistrictsReqVO reqVO) {
        return success(districtService.districts(reqVO));
    }
}
