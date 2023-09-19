package com.hisun.kugga.duke.league.vo.bonus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhou_xiong
 */
@ApiModel("红包计算结果 Response VO")
@Data
public class BonusCalculateRspVO {
    @ApiModelProperty(value = "红包id")
    private Long bonusId;

    @ApiModelProperty(value = "红包分配详情")
    private List<BonusInfo> bonusInfos;
}
