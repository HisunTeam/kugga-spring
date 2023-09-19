package com.hisun.kugga.duke.user.service.user;

import com.hisun.kugga.duke.user.controller.vo.auth.AppAuthUpdatePasswordReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserPageReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserUpdateReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.common.validation.Mobile;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


/**
 * 用户信息 Service 接口
 *
 * @author 芋道源码
 */
public interface DukeUserService {


    /**
     * 修改用户密码
     *
     * @param userId    用户id
     * @param userReqVO 用户请求实体类
     */
    void updatePassword(Long userId, AppAuthUpdatePasswordReqVO userReqVO);

    /**
     * 创建用户信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUser(@Valid UserCreateReqVO createReqVO);

    /**
     * 更新用户信息
     *
     * @param updateReqVO 更新信息
     */
    void updateUser(@Valid UserUpdateReqVO updateReqVO);


    /**
     * 修改用户昵称
     *
     * @param userId   用户id
     * @param nickname 用户新昵称
     */
    void updateUserNickname(Long userId, String nickname);

    /**
     * 修改用户头像
     */
    String updateUserAvatar(Long userId, byte[] bytes, String fileName, String ContentType);

    /**
     * 获得用户信息
     *
     * @param id 编号
     * @return 用户信息
     */
    UserDO getUser(Long id);

    /**
     * 获得用户信息列表
     *
     * @param ids 编号
     * @return 用户信息列表
     */
    List<UserDO> getUserList(Collection<String> ids);

    /**
     * 获得用户信息分页
     *
     * @param pageReqVO 分页查询
     * @return 用户信息分页
     */
    PageResult<UserDO> getUserPage(UserPageReqVO pageReqVO);


    /**
     * 通过手机查询用户
     *
     * @param mobile 手机
     * @return 用户对象
     */
    UserDO getUserByMobile(String mobile);

    /**
     * 通过邮箱查询用户
     *
     * @param email
     * @return
     */
    UserDO getUserByEmail(String email);


    /**
     * 基于手机号创建用户。
     * 如果用户已经存在，则直接进行返回
     *
     * @param mobile     手机号
     * @param registerIp 注册 IP
     * @return 用户对象
     */
    UserDO createUserIfAbsent(@Mobile String mobile, String registerIp);

    /**
     * 更新用户的最后登陆信息
     *
     * @param id      用户编号
     * @param loginIp 登陆 IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword     未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);
}
