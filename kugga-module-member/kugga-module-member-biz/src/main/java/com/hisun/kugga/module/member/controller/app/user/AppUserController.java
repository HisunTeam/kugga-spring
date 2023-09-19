package com.hisun.kugga.module.member.controller.app.user;

import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.security.core.annotations.PreAuthenticated;
import com.hisun.kugga.module.member.controller.app.user.vo.AppUserInfoRespVO;
import com.hisun.kugga.module.member.controller.app.user.vo.AppUserUpdateMobileReqVO;
import com.hisun.kugga.module.member.convert.user.UserConvert;
import com.hisun.kugga.module.member.dal.dataobject.user.MemberUserDO;
import com.hisun.kugga.module.member.service.user.MemberUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hisun.kugga.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Api(tags = "用户 APP - 用户个人中心")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class AppUserController {

    @Resource
    private MemberUserService userService;

    @PutMapping("/update-nickname")
    @ApiOperation("修改用户昵称")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserNickname(@RequestParam("nickname") String nickname) {
        userService.updateUserNickname(getLoginUserId(), nickname);
        return success(true);
    }

    @PutMapping("/update-avatar")
    @ApiOperation("修改用户头像")
    @PreAuthenticated
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

    @GetMapping("/get")
    @ApiOperation("获得基本信息")
    @PreAuthenticated
    public CommonResult<AppUserInfoRespVO> getUserInfo() {
        MemberUserDO user = userService.getUser(getLoginUserId());
        return success(UserConvert.INSTANCE.convert(user));
    }

    @PostMapping("/update-mobile")
    @ApiOperation(value = "修改用户手机")
    @PreAuthenticated
    public CommonResult<Boolean> updateMobile(@RequestBody @Valid AppUserUpdateMobileReqVO reqVO) {
        userService.updateUserMobile(getLoginUserId(), reqVO);
        return success(true);
    }

}

