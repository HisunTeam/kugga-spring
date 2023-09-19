package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("讨论列表查询 request VO")
@Data
public class CommentsReqVO extends PageParam {
    @ApiModelProperty(value = "楼层ID", required = true, example = "1")
    @NotNull(message = "floorId not is null")
    private Long floorId;
}
