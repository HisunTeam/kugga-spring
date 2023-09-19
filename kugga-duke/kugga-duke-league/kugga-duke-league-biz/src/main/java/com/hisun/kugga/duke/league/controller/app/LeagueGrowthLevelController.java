package com.hisun.kugga.duke.league.controller.app;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.league.dal.dataobject.LeagueDO;
import com.hisun.kugga.duke.league.dal.mysql.LeagueGrowthLevelMapper;
import com.hisun.kugga.duke.league.dal.mysql.LeagueMapper;
import com.hisun.kugga.duke.league.service.LeagueGrowthLevelService;
import com.hisun.kugga.duke.league.service.LeagueService;
import com.hisun.kugga.duke.league.vo.rule.LeagueLevelReqVO;
import com.hisun.kugga.duke.league.vo.rule.LeagueLevelRespVO;
import com.hisun.kugga.duke.league.api.dto.UserGrowthLevelDTO;
import com.hisun.kugga.duke.league.vo.rule.LeagueUserLevelRespVO;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;
import static com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * @Author: Lin
 * @Date: 2022-09-13 15:12
 */
@Api(tags = "A10.公会等级设置")
@RestController
@RequestMapping("/league/growthLevel")
@Validated
@Slf4j
public class LeagueGrowthLevelController {

    @Resource
    private LeagueGrowthLevelService levelService;

    @Resource
    private LeagueMapper leagueMapper;
    @Resource
    private LeagueGrowthLevelMapper growthLevelMapper;


    @GetMapping("/getLeagueGrowthLevels")
    @ApiOperation("a.获取公会等级信息")
    public CommonResult<List<LeagueLevelRespVO>> getLeagueGrowthLevels(@Valid LeagueLevelReqVO reqVO) {
        List<LeagueLevelRespVO> leagueLevel = levelService.getLeagueLevel(reqVO);
        return success(leagueLevel);
    }

    @GetMapping("/getUserLeagueGrowthInfos")
    @ApiOperation("b.个人公会等级信息页")
    public CommonResult<LeagueUserLevelRespVO> getUserLeagueGrowthInfos(@Valid LeagueLevelReqVO reqVO) {
        LeagueUserLevelRespVO info = levelService.getUserLeagueGrowthInfos(reqVO.getLeagueId(), getLoginUserId());
        return success(info);
    }

    @PostMapping("/updateLevelNames")
    @ApiOperation("c.修改等级名称")
    public CommonResult<Boolean> updateLevelNames(@Valid @RequestBody LeagueLevelReqVO vo) {
        levelService.updateLeagueLevelName(vo);
        return success(true);
    }


    @GetMapping("/getUserGrowthInfo")
    @ApiOperation("d.获取公会用户等级信息 内部使用")
    public CommonResult<UserGrowthLevelDTO> getUserGrowthInfo(@Valid LeagueLevelReqVO reqVO) {
        UserGrowthLevelDTO info = levelService.getUserGrowthInfo(reqVO.getLeagueId(), getLoginUserId());
        return success(info);
    }

    @GetMapping("/insertLeagueGrowthInfos")
    @ApiOperation("e.初始化公会等级信息")
    public CommonResult insertLeagueGrowthInfos(@Valid LeagueLevelReqVO reqVO) {
        List<LeagueDO> leagueDOList;
        List<Long> leagueIds = new ArrayList<>();
        if(ObjectUtil.isNull(reqVO.getLeagueId())){
            leagueDOList = leagueMapper.selectList();
            leagueIds = leagueDOList.stream().map(LeagueDO::getId).collect(Collectors.toList());
        }else {
            leagueIds.add(reqVO.getLeagueId());
        }

        int count = 0;
        if (ObjectUtil.isNotEmpty(leagueIds)){
            for (Long leagueId : leagueIds) {
                List<LeagueLevelRespVO> vos = growthLevelMapper.selectLevelByLeagueId(leagueId);
                if(ObjectUtil.isNotEmpty(vos)){
                    continue;
                }
                int res = growthLevelMapper.insertInitInfo(leagueId);
                log.info("leagueId:{},count:{}",leagueId,res);
                count++;
            }
        }
        log.info("初始化公会等级数量：{}",count);
        return success();
    }


}
