package com.hisun.kugga.duke.league.vo.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/11/8 15:44
 */
@Data
public class LeagueLevelReqVO {
    @ApiModelProperty(value = "公会ID")
    private Long leagueId;
    private Long userId;

    @ApiModelProperty(value = "公会等级列表")
    List<LevelVo> levels;
}
