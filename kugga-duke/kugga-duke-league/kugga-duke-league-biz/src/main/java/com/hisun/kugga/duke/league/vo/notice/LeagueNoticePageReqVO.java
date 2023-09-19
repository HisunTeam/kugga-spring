package com.hisun.kugga.duke.league.vo.notice;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 16:41
 */
@Data
public class LeagueNoticePageReqVO extends PageParam {

    public static final Integer PAGE_NO_PAGE = 1;
    public static final Integer PAGE_SIZE_PAGE = 10;

    public static final Integer PAGE_NO_MAX = 1;
    public static final Integer PAGE_SIZE_MAX = 100;

    @ApiModelProperty(value = "公会ID不能为空")
    @NotNull(message = "公会ID不能为空")
    private Long id;
}
