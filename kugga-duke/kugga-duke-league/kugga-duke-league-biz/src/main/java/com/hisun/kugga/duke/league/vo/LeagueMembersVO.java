package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zuocheng
 */
@ApiModel("公会成员列表 VO")
@Data
public class LeagueMembersVO {
    @ApiModelProperty(value = "公会ID", required = true, example = "1")
    private Long leagueId;

    @ApiModelProperty(value = "用户在公会的级别", required = true, example = "0:超级管理员,1:管理员,2:普通成员")
    private Integer level;
    @ApiModelProperty(value = "用户公会等级")
    private Integer growthLevel;

    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户加入时间", required = true, example = "2022-07-28 15:56:00")
    private LocalDateTime joinTime;

    @ApiModelProperty(value = "用户头像", example = "https://www.iocoder.cn/xxx.png")
    private String avatar;

    @ApiModelProperty(value = "用户名", required = true, example = "99duke****")
    private String username;

    @ApiModelProperty(value = "昵称", required = true, example = "橘温旧茶")
    private String nickname;

    @ApiModelProperty(value = "姓", example = "LiLi")
    private String lastName;

    @ApiModelProperty(value = "名", example = "ku")
    private String firstName;
}
