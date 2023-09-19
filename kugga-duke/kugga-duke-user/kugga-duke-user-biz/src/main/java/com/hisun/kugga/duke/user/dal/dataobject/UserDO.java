package com.hisun.kugga.duke.user.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.util.Date;

/**
 * 用户信息 DO
 *
 * @author 芋道源码
 */
@TableName("duke_user")
@KeySequence("duke_user_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户名 99duke******
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * lastName 姓
     */
    private String lastName;
    /**
     * firstName 名
     */
    private String firstName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机
     */
    private String phone;
    /**
     * 工作
     */
    private String work;
    /**
     * 国家
     */
    private String country;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 注册 IP
     */
    private String registerIp;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 是否设置支付密码，true：已设置，false：未设置
     */
    @TableField(exist = false)
    private Boolean payPasswordFlag;
}
