package com.hisun.kugga.duke.bos.controller.admin.wallet;

import com.hisun.kugga.duke.bos.controller.admin.wallet.dto.AccountDetailRspBody;
import com.hisun.kugga.duke.bos.service.wallet.PlatformAccountService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author: lzt
 */
@Api(tags = "管理后台 - 平台钱款查询")
@RestController
@RequestMapping("/duke/channel-wallet")
public class PlatformAccountController {
    @Resource
    private PlatformAccountService platformAccountService;

    @GetMapping("/getPlatformAccount")
    @ApiOperation("查询密码授权是否在有效期内")
    public CommonResult<BigDecimal> getPlatformAccount() {
        return success(platformAccountService.selectAccountDetail());
    }


}
