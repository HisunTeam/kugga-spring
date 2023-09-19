package com.hisun.kugga.duke.league.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 论坛 Response ForumVO")
@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
@TableName("duke_forum")
public class ForumVO {

    @ApiModelProperty(value = "编号", required = true, example = "1024")
    @NotNull(message = "论坛编号不能为空")
    @TableId
    private Long id;

    @ApiModelProperty(value = "名称", required = true, example = "1024")
    @NotNull(message = "论坛名称不能为空")
    private String name;

    @ApiModelProperty(value = "论坛所属的公会ID", required = true, example = "1024")
    private Long leagueId;

/*    @ApiModelProperty(value = "创建时间", required = true)
    @TableField(exist = false)
    private Date createTime;*/
}
