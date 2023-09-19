package com.hisun.kugga.duke.bos.controller.admin.serialpassword;

import com.hisun.kugga.duke.bos.controller.admin.serialpassword.vo.SerialPasswordUpdateReqVO;
import com.hisun.kugga.duke.bos.service.serialpassword.SerialPasswordService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 序列密码信息")
@RestController
@RequestMapping("/duke/serial-password")
@Validated
public class SerialPasswordController {

    @Resource
    private SerialPasswordService serialPasswordService;


    @GetMapping("/judgeEffective")
    @ApiOperation("查询密码授权是否在有效期内")
//    @PreAuthorize("@ss.hasPermission('duke:password:select')")
    public CommonResult<Boolean> judgePasswordEffective() {
        return success(serialPasswordService.judgePasswordEffective());
    }

    @PostMapping("/modifyStatus")
    @ApiOperation("修改密码输入状态")
//    @PreAuthorize("@ss.hasPermission('duke:password:select')")
    public CommonResult<Boolean> modifyPasswordStatus(@Valid @RequestBody SerialPasswordUpdateReqVO updateReqVO) {
        return success(serialPasswordService.modifyPasswordStatus(updateReqVO));
    }

}
