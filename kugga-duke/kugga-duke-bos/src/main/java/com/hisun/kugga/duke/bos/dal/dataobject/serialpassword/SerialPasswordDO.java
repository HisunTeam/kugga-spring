package com.hisun.kugga.duke.bos.dal.dataobject.serialpassword;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 序列密码信息 DO
 *
 * @author 芋道源码
 */
@TableName("duke_serial_password")
@KeySequence("duke_serial_password_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerialPasswordDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 推荐人编号
     */
    private Integer sortId;
    /**
     * 密码组编号
     */
    private String groupName;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 序列密码
     */
    private String serialPassword;
    /**
     * 密码输入间隔时间
     */
    private Long intervalTime;
    /**
     * 授权生效时间
     */
    private Long effectiveTime;
    /**
     * 密码输入状态
     */
    private String inputFlag;
    /**
     * 输入密码时间
     */
    private LocalDateTime inputTime;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
