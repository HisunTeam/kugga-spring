package com.hisun.kugga.duke.user.api.oauth2;

import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;


/**
 * @Description: 获取用户信息
 * @author： Lin
 * @Date 2022/7/27 14:51
 */
public interface DukeUserApi {

    /**
     * 获得用户信息
     *
     * @param id 编号
     * @return 用户信息
     */
    UserInfoRespDTO getUserById(Long id);

    /**
     * 根据邮箱查询用户
     *
     * @param mail
     * @return
     */
    UserInfoRespDTO getUserByEmail(String mail);
}
