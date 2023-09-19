package com.hisun.kugga.duke.pay.controller.app.userbill;

import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.userbill.vo.UserBillPageRspVO;
import com.hisun.kugga.duke.pay.service.userbill.UserBillService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;


@Api(tags = "用户账单")
@RestController
@RequestMapping("/duke/user-bill")
@Validated
public class UserBillController {

    @Resource
    private UserBillService userBillService;

    @GetMapping("/page")
    @ApiOperation("获得用户账单分页")
    public CommonResult<PageResult<UserBillPageRspVO>> getUserBillPage(@Valid UserBillPageReqVO pageVO) {
        PageResult<UserBillPageRspVO> pageResult = userBillService.getUserBillPage(pageVO);
        return success(pageResult);
    }
}
