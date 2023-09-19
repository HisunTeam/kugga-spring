package com.hisun.kugga.duke.user.controller.app;

import cn.hutool.core.io.IoUtil;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.user.controller.vo.auth.AppAuthUpdatePasswordReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserInfoRespVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserUpdateReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.duke.user.service.user.DukeUserService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.image.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;


@Api(tags = "A2-用户信息")
@RestController
@RequestMapping("/user/info")
@Validated
public class DukeUserController {

    @Resource
    private DukeUserService dukeUserService;
    @Resource
    private WalletApi walletApi;


    @PostMapping("/update-password")
    @ApiOperation(value = "d.修改用户密码")
    public CommonResult<Boolean> updatePassword(@RequestBody @Valid AppAuthUpdatePasswordReqVO reqVO) {
        dukeUserService.updatePassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-nickname")
    @ApiOperation("c.修改用户昵称")
    public CommonResult<Boolean> updateUserNickname(@RequestParam("nickname") String nickname) {
        dukeUserService.updateUserNickname(getLoginUserId(), nickname);
        return success(true);
    }

    @PutMapping("/update-info")
    @ApiOperation("c.修改用户信息")
    public CommonResult<Boolean> updateUserInfo(@RequestBody @Valid UserUpdateReqVO updateReqVO) {
        updateReqVO.setId(getLoginUserId());
        dukeUserService.updateUser(updateReqVO);
        return success(true);
    }


    @PutMapping("/update-avatar")
    @ApiOperation("b.修改用户头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        if (!ImageUtils.support(file.getOriginalFilename())) {
            throw exception(FILE_IMAGE_NOT_SUPPORT);
        }
        if (ImageUtils.sizeLimit(file.getSize())) {
            throw exception(FILE_IMAGE_SIZE_LIMIT);
        }
        byte[] bytes = IoUtil.readBytes(file.getInputStream());
        String avatar = dukeUserService.updateUserAvatar(getLoginUserId(), bytes, file.getOriginalFilename(), file.getContentType());
        return success(avatar);
    }

    @GetMapping("/getUser")
    @ApiOperation("a.获得用户信息")
    public CommonResult<UserInfoRespVO> getUser(@RequestParam(value = "id", required = false) Long id) {
        /*if (id == null) {
            id = getLoginUserId();
        }*/
        UserDO user = dukeUserService.getUser(getLoginUserId());
        UserInfoRespVO respVO = new UserInfoRespVO();
        BeanUtils.copyProperties(user, respVO);
        return success(respVO);
    }

}
