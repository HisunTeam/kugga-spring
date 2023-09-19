package com.hisun.kugga.duke.league.vo.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/11/8 15:44
 */
@Data
public class LeagueUserLevelRespVO {
    @ApiModelProperty(value = "公会名称")
    private String leagueName;
    @ApiModelProperty(value = "公会头像")
    private String leagueAvatar;

    @ApiModelProperty(value = "姓", example = "99")
    private String lastName;
    @ApiModelProperty(value = "名", example = "100")
    private String firstName;
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户在公会的级别(用户是当前公会成员时,则存在值)", required = true, example = " 0:超级管理员 1:管理员 2:成员  其它:非公会成员")
    private Integer userLevel;
    @ApiModelProperty(value = "用户公会等级")
    private Integer growthLevel;
    @ApiModelProperty(value = "积分值")
    private Integer growthValue;
    @ApiModelProperty(value = "积分等级名称")
    private String levelName;

    @ApiModelProperty(value = "订阅过期时间")
    private LocalDateTime expireTime;

}
