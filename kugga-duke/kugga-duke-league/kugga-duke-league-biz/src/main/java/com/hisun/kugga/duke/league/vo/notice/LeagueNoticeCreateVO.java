package com.hisun.kugga.duke.league.vo.notice;

import com.hisun.kugga.duke.league.bo.task.TaskLeagueBO;
import com.hisun.kugga.duke.league.vo.user.UserBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 * 公会公告栏 工厂接收任务的参数对象
 */
@Data
public class LeagueNoticeCreateVO {
    @ApiModelProperty(value = "用户选择的公会列表")
    private List<TaskLeagueBO> leagueList;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 公告类型
     */
    private Integer type;

    /**
     * 动作发起者
     */
    private Long userId;
    private String name;
    private String username;

    private BigDecimal amount;

    /**
     * 公会ID和名称
     */
    private Long leagueId;
    private String leagueName;

    private UserBO userInfo;

    /**
     * 动作被邀请者
     */
    private String byUserId;
    private String byName;
    private String byUsername;
}
