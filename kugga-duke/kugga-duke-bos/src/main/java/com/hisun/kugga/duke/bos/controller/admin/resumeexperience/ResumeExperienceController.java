package com.hisun.kugga.duke.bos.controller.admin.resumeexperience;

import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceBatchUpdateReqVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperiencePageReqVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceRespVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceUpdateReqVO;
import com.hisun.kugga.duke.bos.service.resumeexperience.ResumeExperienceService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 个人简历经历")
@RestController
@RequestMapping("/duke/resume-experience")
@Validated
public class ResumeExperienceController {

    @Resource
    private ResumeExperienceService resumeExperienceService;


    @PostMapping("/update")
    @ApiOperation("更新个人简历经历")
    public CommonResult<Boolean> updateResumeExperience(@Valid @RequestBody ResumeExperienceUpdateReqVO updateReqVO) {
        resumeExperienceService.updateResumeExperience(updateReqVO);
        return success(true);
    }

    @PostMapping("/updateBatch")
    @ApiOperation("更新个人简历经历")
    public CommonResult<Boolean> updateResumeExperienceBatch(@Valid @RequestBody ResumeExperienceBatchUpdateReqVO updateReqVO) {
        resumeExperienceService.updateResumeExperienceBatch(updateReqVO);
        return success(true);
    }

    @GetMapping("/cert-type")
    @ApiOperation("所有订单类型")
    public CommonResult<Map<String, String>> selectAllCertType() {
        return success(resumeExperienceService.selectAllCertType());
    }


    @PostMapping("/page")
    @ApiOperation("获得个人简历经历分页")
    public CommonResult<PageResult<ResumeExperienceRespVO>> getResumeExperiencePage(@Valid @RequestBody ResumeExperiencePageReqVO pageVO) {
        return success(resumeExperienceService.getResumeExperiencePage(pageVO));

    }


}
