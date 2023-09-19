package com.hisun.kugga.duke.user.service.user.impl;

import cn.hutool.core.util.IdUtil;
import com.hisun.kugga.duke.enums.SecretTypeEnum;
import com.hisun.kugga.duke.pay.api.useraccount.UserAccountApi;
import com.hisun.kugga.duke.pay.api.wallet.WalletApi;
import com.hisun.kugga.duke.pay.api.wallet.dto.CreateAccountReqBody;
import com.hisun.kugga.duke.pay.api.wallet.dto.CreateAccountRspBody;
import com.hisun.kugga.duke.system.api.rsa.PasswordDecryptApi;
import com.hisun.kugga.duke.system.api.rsa.dto.DecryptDTO;
import com.hisun.kugga.duke.system.api.s3.S3FileUploadApi;
import com.hisun.kugga.duke.user.controller.vo.auth.AppAuthUpdatePasswordReqVO;
import com.hisun.kugga.duke.user.controller.vo.auth.UserAccountVo;
import com.hisun.kugga.duke.user.controller.vo.user.UserCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserPageReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserUpdateReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.duke.user.dal.mysql.DukeUserMapper;
import com.hisun.kugga.duke.user.service.user.DukeUserService;
import com.hisun.kugga.framework.common.enums.CommonStatusEnum;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.common.util.amount.AmountUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.common.util.servlet.ServletUtils.getClientIP;


/**
 * 用户信息 Service 实现类
 *
 * @author LinCheng
 */
@Service
@Validated
public class DukeUserServiceImpl implements DukeUserService {

    @Resource
    private DukeUserMapper userMapper;

    @Resource
    private WalletApi walletApi;

    @Resource
    private S3FileUploadApi s3FileUploadApi;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserAccountApi userAccountApi;
    @Resource
    private PasswordDecryptApi decryptApi;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Long createUser(UserCreateReqVO createReqVO) {
        // 用户创建
        UserDO user = new UserDO();
        user.setUsername(createReqVO.getUsername());
        // 加密密码
        user.setPassword(passwordEncoder.encode(createReqVO.getPassword()));
        user.setEmail(createReqVO.getEmail());
        user.setNickname(createReqVO.getNickname());
        // 姓 名
        user.setLastName(createReqVO.getLastName());
        user.setFirstName(createReqVO.getFirstName());
        user.setStatus(CommonStatusEnum.ENABLE.getStatus());
        user.setRegisterIp(getClientIP());
        userMapper.insert(user);

        //外部钱包账户开通
        CreateAccountReqBody createAccountReqBody = new CreateAccountReqBody();
        CreateAccountRspBody accountRspBody = walletApi.createAccount(createAccountReqBody);

        //分转元
        BigDecimal balance = AmountUtil.fenToYuan(accountRspBody.getBalance());
        UserAccountVo userAccountVo = new UserAccountVo()
                .setUserId(user.getId())
                .setAccountId(accountRspBody.getAccount())
                .setBalance(balance);
        userMapper.insertUserAccount(userAccountVo);
        return user.getId();
    }

    @Override
    @CacheEvict(value = "cacheable:dukeUser", key = "#updateReqVO.id")
    public void updateUser(UserUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateUserExists(updateReqVO.getId());
        UserDO updateObj = new UserDO();
        BeanUtils.copyProperties(updateReqVO, updateObj);
        userMapper.updateById(updateObj);
    }

    @Override
    public void updateUserNickname(Long userId, String nickname) {
        UserDO user = this.checkUserExists(userId);
        // 仅当新昵称不等于旧昵称时进行修改
        if (nickname.equals(user.getNickname())) {
            return;
        }
        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setNickname(nickname);
        userMapper.updateById(userDO);
    }

    @Override
    @CacheEvict(value = "cacheable:dukeUser", key = "#userId")
    public String updateUserAvatar(Long userId, byte[] avatarFile, String fileName, String contentType) {
        // 创建文件
        String avatar = s3FileUploadApi.uploadUserAvatar(userId, avatarFile, fileName, contentType);
        // 更新头像路径
        userMapper.updateById(UserDO.builder()
                .id(userId)
                .avatar(avatar)
                .build());
        return avatar;
    }


    @Override
    public void updatePassword(Long userId, AppAuthUpdatePasswordReqVO reqVO) {
        /*if (ObjectUtil.notEqual(reqVO.getPassword(), reqVO.getEnsurePwd())) {
            throw exception(PASSWORD_ENSURE_NOT_MATCH);
        }*/
        //解密新老密码
        String oldPassword = decryptPassword(reqVO.getPublicKey(), reqVO.getOldPassword(), SecretTypeEnum.LOGIN_UPDATE);
        String password = decryptPassword(reqVO.getPublicKey(), reqVO.getPassword(), SecretTypeEnum.LOGIN_UPDATE);
        reqVO.setOldPassword(oldPassword);
        reqVO.setPassword(password);

        // 检验旧密码
        UserDO userDO = checkOldPassword(userId, reqVO.getOldPassword());
        //新老密码不能一致
        if (passwordEncoder.matches(reqVO.getPassword(), userDO.getPassword())) {
            throw exception(NEW_OLD_PASSWORD_NOT_EQUALS);
        }
        // 更新用户密码
        userMapper.updateById(UserDO.builder().id(userDO.getId())
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    /**
     * @param publicKey
     * @param password
     * @param type
     * @return
     */
    private String decryptPassword(String publicKey, String password, SecretTypeEnum type) {
        DecryptDTO decryptDTO = new DecryptDTO()
                .setPublicKey(publicKey)
                .setPassword(password)
                .setType(type);
        return decryptApi.decrypt(decryptDTO);
    }


    private void validateUserExists(Long id) {
        if (userMapper.selectById(id) == null) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    /**
     * 缓存用户信息
     */
    @Override
    @Cacheable(cacheNames = "cacheable:dukeUser", key = "#id")
    public UserDO getUser(Long id) {
        this.validateUserExists(id);
        UserDO userDO = userMapper.selectById(id);
        userDO.setPassword(null);
        userDO.setPayPasswordFlag(userAccountApi.getPayPasswordFlag(id));
        return userDO;
    }

    @Override
    public List<UserDO> getUserList(Collection<String> ids) {
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<UserDO> getUserPage(UserPageReqVO pageReqVO) {
        return userMapper.selectPage(pageReqVO);
    }


    @Override
    public UserDO getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public UserDO getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public UserDO createUserIfAbsent(String mobile, String registerIp) {
        // 用户已经存在
        UserDO user = userMapper.selectByMobile(mobile);
        if (user != null) {
            return user;
        }
        // 用户不存在，则进行创建
        return this.createUser(mobile, registerIp);
    }

    private UserDO createUser(String mobile, String registerIp) {
        // 生成密码
        String password = IdUtil.fastSimpleUUID();
        // 插入用户
        UserDO user = new UserDO();
        user.setPhone(mobile);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setPassword(encodePassword(password)); // 加密密码
        user.setRegisterIp(registerIp);
        userMapper.insert(user);
        return user;
    }

    @Override
    public void updateUserLogin(Long userId, String loginIp) {
        userMapper.updateById(new UserDO().setId(userId)
                .setLoginIp(loginIp).setLoginDate(new Date()));
    }


    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 校验旧密码
     *
     * @param id          用户 id
     * @param oldPassword 旧密码
     * @return UserDO 用户实体
     */
    public UserDO checkOldPassword(Long id, String oldPassword) {
        UserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        // 参数：未加密密码，编码后的密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw exception(USER_PASSWORD_FAILED);
        }
        return user;
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserDO checkUserExists(Long id) {
        if (id == null) {
            return null;
        }
        UserDO user = userMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }
}
