package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("创建公会预下单 Request VO")
@Data
public class CreatePreOrderReqVO {
    @ApiModelProperty(value = "公会名称", required = true, example = "一时躺平一时爽，一直躺平一直爽")
    @NotBlank(message = "公会名称不能为空")
    @Size(max = 128, message = "公会名称长度超限")
    private String leagueName;

    @ApiModelProperty(value = "公会头像", example = "https://www.domain.com/public/image.jpeg")
    private String leagueAvatar;

    @ApiModelProperty(value = "公会描述", example = "躺平身体健康,卷王谁都不好过")
    @Size(max = 1024, message = "公会描述长度超限")
    private String leagueDesc;

    @ApiModelProperty(value = "金额(元)", example = "10")
    private BigDecimal amt;
}
