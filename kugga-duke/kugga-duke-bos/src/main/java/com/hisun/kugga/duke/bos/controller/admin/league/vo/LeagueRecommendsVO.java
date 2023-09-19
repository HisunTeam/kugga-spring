package com.hisun.kugga.duke.bos.controller.admin.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公会详情
 *
 * @author zuocheng
 */
@ApiModel("公会详情 VO")
@Data
public class LeagueRecommendsVO {

    @ApiModelProperty(value = "公会编号", required = true, example = "1")
    private Long leagueId;

    @ApiModelProperty(value = "排序编号", required = true, example = "1")
    private Integer sortId;

    @ApiModelProperty(value = "公会名称", required = true, example = "时躺平一时爽，一直躺平一直爽")
    private String leagueName;

    @ApiModelProperty(value = "成员人数", example = "99")
    private String members;

    @ApiModelProperty(value = "推荐报告数量", example = "100")
    private String recommendations;

    @ApiModelProperty(value = "公会标签类型", example = "1")
    private String leagueLabel;

    @ApiModelProperty(value = "公会是否已被认证", example = "true:已认证,false:未认证")
    private Boolean authFlag;

    @ApiModelProperty(value = "创建时间", example = "2022-08-22 14:59:37")
    private LocalDateTime createTime;

}
