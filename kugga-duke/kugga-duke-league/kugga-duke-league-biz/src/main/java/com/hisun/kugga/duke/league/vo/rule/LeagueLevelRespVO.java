package com.hisun.kugga.duke.league.vo.rule;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/11/8 15:44
 */
@Data
public class LeagueLevelRespVO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "成长等级")
    private Integer growthLevel;
    @ApiModelProperty(value = "等级名称")
    private String levelName;
    @ApiModelProperty(value = "最小值 [0,10]")
    private Integer levelMin;
    @ApiModelProperty(value = "最大值")
    private Integer levelMax;
}
