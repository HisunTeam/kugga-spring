package com.hisun.kugga.duke.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 加入公会审批状态值
 * @author： Lin
 * @Date 2022/9/13 17:18
 */
@Getter
@AllArgsConstructor
public enum LeagueJoinApprovalTypeEnum {
    /**
     * 业务状态 0初始化未审批、1已同意、2已拒绝、3已过期
     */
    INIT(0, "初始化未审批"),
    AGREE(1, "已同意"),
    REJECT(2, "已拒绝"),
    EXPIRE(3, "已过期");
    @EnumValue
    int value;
    String desc;
}
