package com.hisun.kugga.duke.league.controller.app;

import cn.hutool.core.io.IoUtil;
import com.hisun.kugga.duke.league.service.LeagueRuleService;
import com.hisun.kugga.duke.league.vo.rule.LeagueJoinRuleVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueRuleVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.image.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:12
 */
@Api(tags = "公会规则")
@RestController
@RequestMapping("/league/rule")
@Validated
@Slf4j
public class LeagueRuleController {

    @Resource
    private LeagueRuleService leagueRuleService;

    @GetMapping("/get")
    @ApiOperation("根据公会ID查询公会规则")
    @ApiImplicitParam(name = "id", value = "公会id", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<LeagueRuleVO> get(@RequestParam("id") Long id) {
        LeagueRuleVO leagueRuleVO = leagueRuleService.get(id);
        return success(leagueRuleVO);
    }

    @PostMapping("/update")
    @ApiOperation("修改公会规则")
    public CommonResult updateRule(@Valid @RequestBody LeagueRuleVO vo) {
        return leagueRuleService.update(vo);
    }

    @PutMapping("/update-league-avatar")
    @ApiOperation("更新工会头像")
    public CommonResult<String> updateLeagueAvatar(@RequestParam("avatarFile") MultipartFile file, @RequestParam("id") Long id) throws IOException {
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
        String avatar = leagueRuleService.updateAvatar(id, bytes, file.getOriginalFilename(), file.getContentType());
        return success(avatar);
    }

    @GetMapping("/getLeagueJoinInfo")
    @ApiOperation("查询公会信息和公会加入规则")
    public CommonResult<LeagueRuleVO> getLeagueRuleInfo(@RequestParam("id") Long id) {
        LeagueRuleVO leagueRuleVO = leagueRuleService.getLeagueRuleInfo(id);
        return success(leagueRuleVO);
    }

    @GetMapping("/getLeagueJoinRule")
    @ApiOperation("根据公会ID查询公会是否可加入及订阅")
    public CommonResult<LeagueJoinRuleVO> getLeagueJoinRule(@RequestParam("id") Long id) {
        LeagueJoinRuleVO leagueJoinRule = leagueRuleService.getLeagueJoinRule(id);
        return success(leagueJoinRule);
    }
}
