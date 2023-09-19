package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zuocheng
 */
@ApiModel("邀请信息 Response VO")
@Data
public class InviteDataRespVO {
    @ApiModelProperty(value = "公会ID", example = "001")
    private Long leagueId;

    @ApiModelProperty(value = "公会名称", example = "一时躺平一时爽，一直躺平一直爽")
    private String leagueName;

    @ApiModelProperty(value = "公会描述", example = "躺平身体健康,卷王谁都不好过")
    private String leagueDesc;

    @ApiModelProperty(value = "公会图标", example = "/img/tp.png")
    private String leagueAvatar;

    @ApiModelProperty(value = "公会激活标识", example = "true:已激活,false:未激活")
    private Boolean leagueActivationFlag;

    @ApiModelProperty(value = "公会创建时间", example = "2022-07-26 20:22:01")
    private LocalDateTime leagueCreateTime;

    @ApiModelProperty(value = "用户ID", example = "01")
    private Long userId;

    @ApiModelProperty(value = "用户名", example = "张三")
    private String userName;

    @ApiModelProperty(value = "昵称", example = "菜刀砍电线火花带闪电")
    private String nickName;

    @ApiModelProperty(value = "姓", example = "张")
    private String lastName;

    @ApiModelProperty(value = "名", example = "三")
    private String firstName;

    @ApiModelProperty(value = "当前用户是否已加入该公会 true:已加入 false:未加入", example = "false")
    private Boolean joinedFlag;
}
