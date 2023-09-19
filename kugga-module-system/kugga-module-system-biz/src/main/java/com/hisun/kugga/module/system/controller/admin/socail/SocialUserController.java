package com.hisun.kugga.module.system.controller.admin.socail;

import com.hisun.kugga.framework.common.enums.UserTypeEnum;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.module.system.controller.admin.socail.vo.SocialUserBindReqVO;
import com.hisun.kugga.module.system.controller.admin.socail.vo.SocialUserUnbindReqVO;
import com.hisun.kugga.module.system.convert.social.SocialUserConvert;
import com.hisun.kugga.module.system.service.social.SocialUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Api(tags = "管理后台 - 社交用户")
@RestController
@RequestMapping("/system/social-user")
@Validated
public class SocialUserController {

    @Resource
    private SocialUserService socialUserService;

    @PostMapping("/bind")
    @ApiOperation("社交绑定，使用 code 授权码")
    public CommonResult<Boolean> socialBind(@RequestBody @Valid SocialUserBindReqVO reqVO) {
        socialUserService.bindSocialUser(SocialUserConvert.INSTANCE.convert(getLoginUserId(), UserTypeEnum.ADMIN.getValue(), reqVO));
        return CommonResult.success(true);
    }

    @DeleteMapping("/unbind")
    @ApiOperation("取消社交绑定")
    public CommonResult<Boolean> socialUnbind(@RequestBody SocialUserUnbindReqVO reqVO) {
        socialUserService.unbindSocialUser(getLoginUserId(), UserTypeEnum.ADMIN.getValue(), reqVO.getType(), reqVO.getOpenid());
        return CommonResult.success(true);
    }

}
