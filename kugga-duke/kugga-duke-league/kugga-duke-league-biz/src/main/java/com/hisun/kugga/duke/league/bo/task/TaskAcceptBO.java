package com.hisun.kugga.duke.league.bo.task;

import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskAcceptBO {
    @ApiModelProperty(value = "任务表ID", example = "1024")
    private Long id;
    @ApiModelProperty(value = "任务类型", required = true, example = "1")
    private TaskTypeEnum type;
    @ApiModelProperty(value = "公会公告栏ID", required = true, example = "1024")
    private Long leagueNoticeId;
    @ApiModelProperty(value = "公会公告栏类型", required = true, example = "1")
    private LeagueNoticeTypeEnum leagueNoticeType;
}
