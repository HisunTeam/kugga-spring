package com.hisun.kugga.duke.league.bo.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
@ApiModel("InitTask接口返回对象")
public class TaskFinishResultBO {

    @ApiModelProperty(value = "任务类型1传值列表 写推荐报告")
    private TaskFinish1 type1;
    @ApiModelProperty(value = "任务类型1传值列表 公会认证")
    private TaskFinish2 type2;
    @ApiModelProperty(value = "任务类型1传值列表 聊天")
    private TaskFinish3 type3;

    @Data
    public class TaskFinish1 {
    }

    @Data
    public class TaskFinish2 {

    }

    @Data
    public class TaskFinish3 {
        /*@ApiModelProperty(value = "是否同意聊天 true同意聊天 false拒绝")
        private Boolean agreeOrNo;*/
    }
}
