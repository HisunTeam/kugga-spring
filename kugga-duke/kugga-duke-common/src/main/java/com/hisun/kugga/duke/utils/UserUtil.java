package com.hisun.kugga.duke.utils;

/**
 * @Description: 用户名拼接
 * @author： Lin
 * @Date 2022/8/30 15:01
 */
public class UserUtil {

    /**
     * 拼接用户名
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public static String getUserName(String firstName, String lastName) {
        return firstName + lastName;
    }
}
