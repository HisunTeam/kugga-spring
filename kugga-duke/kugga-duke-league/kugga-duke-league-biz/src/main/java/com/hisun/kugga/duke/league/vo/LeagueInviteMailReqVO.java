package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("公会邀请邮件 Request VO")
@Data
public class LeagueInviteMailReqVO {

    @ApiModelProperty(value = "公会ID", required = true, example = "001")
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;

    @ApiModelProperty(value = "邀请连接绑定ID", required = true, example = "1")
    @NotNull(message = "邀请连接绑定ID不能为空")
    private Long binId;

    @ApiModelProperty(value = "被邀请者邮件", required = true)
    @NotEmpty(message = "被邀请人邮箱不能为空")
    private List<String> mails;
}
