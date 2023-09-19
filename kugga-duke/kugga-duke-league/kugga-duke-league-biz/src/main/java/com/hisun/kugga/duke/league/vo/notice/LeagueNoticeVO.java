package com.hisun.kugga.duke.league.vo.notice;

import com.hisun.kugga.duke.league.dal.dataobject.LeagueNoticeDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 16:41
 */
@Data
public class LeagueNoticeVO extends LeagueNoticeDO {

    @ApiModelProperty(value = "主动用户 姓名")
    private String useFirstName;
    @ApiModelProperty(value = "主动用户 姓名")
    private String useLastName;
    @ApiModelProperty(value = "主动用户 公会名")
    private String useLeagueName;

    @ApiModelProperty(value = "被动用户 姓名")
    private String byFirstName;
    @ApiModelProperty(value = "被动用户 姓名")
    private String byLastName;
    @ApiModelProperty(value = "被动用户 公会名")
    private String byLeagueName;
}
