package com.hisun.kugga.duke.league.vo.user;


import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/27 14:51
 */
@Data
@ToString(callSuper = true)
public class UserBO {

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
     * 头像
     */
    private String avatar;
}
