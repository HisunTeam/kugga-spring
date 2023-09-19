package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("模糊匹配标签列表 request VO")
@Data
public class VagueLabelsReqVO extends PageParam {
    @ApiModelProperty(value = "待查询的字符串", required = true, example = "哈哈哈")
    private String searchStr;
}
