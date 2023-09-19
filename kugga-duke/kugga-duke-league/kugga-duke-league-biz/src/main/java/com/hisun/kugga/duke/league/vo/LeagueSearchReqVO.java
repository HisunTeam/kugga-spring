package com.hisun.kugga.duke.league.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zuocheng
 */
@ApiModel("公会搜索")
@Data
@EqualsAndHashCode(callSuper = true)
public class LeagueSearchReqVO extends PageParam {

    @ApiModelProperty(value = "搜索内容", required = false, example = "Duke")
    private String content;

    private Long userId;
}
