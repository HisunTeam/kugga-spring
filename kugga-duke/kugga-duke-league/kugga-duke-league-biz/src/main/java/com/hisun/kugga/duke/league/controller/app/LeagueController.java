package com.hisun.kugga.duke.league.controller.app;

import cn.hutool.core.io.IoUtil;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.vo.*;
import com.hisun.kugga.duke.league.vo.bonus.BonusCalculateRspVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.pojo.PageParam;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.common.util.image.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author 泡面3分钟
 */
@Api(tags = "公会 APP")
@RestController
@RequestMapping("/league")
@Validated
@Slf4j
public class LeagueController {
    @Resource
    private LeagueService leagueService;

    @PostMapping("/create-upload-avatar")
    @ApiOperation("创建公会上传头像")
    public CommonResult<String> createUploadAvatar(@RequestParam("avatarFile") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        if (ImageUtils.sizeLimit(file.getSize())) {
            throw exception(FILE_IMAGE_SIZE_LIMIT);
        }
        if (!ImageUtils.support(file.getOriginalFilename())) {
            throw exception(FILE_IMAGE_NOT_SUPPORT);
        }
        byte[] bytes = IoUtil.readBytes(file.getInputStream());
        String avatar = leagueService.createUploadAvatar(bytes, file.getOriginalFilename(), file.getContentType());
        return success(avatar);
    }

    /**
     * 创建公会预下单
     *
     * @return
     */
    @PostMapping("/create-pre-order")
    @ApiOperation("创建公会预下单")
    public CommonResult<CreatePreOrderRespVO> createPreOrder(@Valid @RequestBody CreatePreOrderReqVO reqVO) {
        return success(leagueService.createPreOrder(reqVO));
    }

    @PostMapping("/create")
    @ApiOperation("创建公会")
    public CommonResult<LeagueCreateRespVO> create(@Valid @RequestBody LeagueCreateReqVO reqVO) {
        return success(leagueService.create(reqVO));
    }

    @PostMapping("/query-create-price")
    @ApiOperation("查询公会创建价格")
    public CommonResult<LeagueCreatePriceRespVO> createPrice() {
        return success(leagueService.createPrice());
    }

    @PostMapping("/invite-url")
    @ApiOperation("获取公会邀请连接")
    public CommonResult<LeagueInviteUrlRespVO> getInviteUrl(@Valid @RequestBody LeagueInviteUrlReqVO reqVO) {
        return success(leagueService.getInviteUrl(reqVO));
    }

    @GetMapping("/invite/{uuid}")
    @ApiOperation("获取邀请公会信息")
    public CommonResult<InviteDataRespVO> getInviteData(@PathVariable String uuid) {
        return success(leagueService.getInviteData(uuid));
    }

    @PostMapping("/join")
    @ApiOperation("加入公会")
    public CommonResult<Boolean> joinLeague(@Valid @RequestBody JoinLeagueReqVO reqVO) {
        leagueService.joinLeague(reqVO);
        return success(true);
    }

    @PostMapping("/invite-mail")
    @ApiOperation("公会发起邮件邀请")
    public CommonResult<Boolean> inviteMail(@Valid @RequestBody LeagueInviteMailReqVO reqVO) {
        leagueService.inviteMail(reqVO);
        return success(true);
    }

    @PostMapping("/recommend-leagues")
    @ApiOperation("查询推荐公会列表")
    public CommonResult<PageResult<LeagueRecommendsVO>> recommendLeagues(@Valid @RequestBody LeagueSearchReqVO reqVO) {
        return success(leagueService.recommendLeagues(reqVO));
    }

    @PostMapping("/search-leagues")
    @ApiOperation("公会名称搜素")
    public CommonResult<PageResult<LeagueRecommendsVO>> searchLeagues(@Valid @RequestBody LeagueSearchReqVO reqVO) {
        return success(leagueService.searchLeagues(reqVO));
    }

    @PostMapping("/recommend-auth-leagues")
    @ApiOperation("查询具有认证权限的公会列表")
    public CommonResult<PageResult<LeagueAndRuleVO>> recommendAuthLeagues(@Valid @RequestBody PageParam reqVO) {
        return success(leagueService.recommendAuthLeagues(reqVO));
    }

    @PostMapping("/details")
    @ApiOperation("获取公会详情")
    public CommonResult<LeagueDetailsRespVO> details(@Valid @RequestBody LeagueDetailsReqVO reqVO) {
        return success(leagueService.details(reqVO));
    }

    @PostMapping("/members")
    @ApiOperation("获取公会成员列表")
    public CommonResult<PageResult<LeagueMembersVO>> members(@Valid @RequestBody LeagueMembersPageReqVO reqVO) {
        return success(leagueService.members(reqVO));
    }

    @PostMapping("/user-leagues")
    @ApiOperation("查询用户公会列表")
    public CommonResult<PageResult<UserLeaguesVO>> userLeagues(@Valid @RequestBody UserLeaguesPageReqVO reqVO) {
        return success(leagueService.userLeagues(reqVO));
    }

    @PostMapping("/account")
    @ApiOperation("查询公会账户额度")
    public CommonResult<LeagueAccountRespVO> leagueAccount(@Valid @RequestBody LeagueAccountReqVO reqVO) {
        return success(leagueService.leagueAccount(reqVO));
    }

    @PostMapping("/check-mail-is-league-member")
    @ApiOperation("检查填写的邮箱是否已经是公会成员")
    public CommonResult<CheckMailIsLeagueMemberRespVO> checkMailIsLeagueMember(@Valid @RequestBody CheckMailIsLeagueMemberReqVO reqVO) {
        return success(leagueService.checkMailIsLeagueMember(reqVO));
    }
}
