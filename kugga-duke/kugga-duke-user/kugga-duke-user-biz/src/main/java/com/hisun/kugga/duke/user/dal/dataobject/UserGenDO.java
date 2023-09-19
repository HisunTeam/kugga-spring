package com.hisun.kugga.duke.user.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户号自增 DO
 *
 * @author 芋道源码
 */
@TableName("duke_user_gen")
@KeySequence("duke_user_seq")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGenDO {

    /**
     * 地区前缀
     */
    private String regionPrefix;
    /**
     * 序号
     */
    private String number;

}
