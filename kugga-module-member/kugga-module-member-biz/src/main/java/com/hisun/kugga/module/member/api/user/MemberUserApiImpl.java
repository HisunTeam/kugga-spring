package com.hisun.kugga.module.member.api.user;

import com.hisun.kugga.module.member.api.user.dto.UserRespDTO;
import com.hisun.kugga.module.member.convert.user.UserConvert;
import com.hisun.kugga.module.member.dal.dataobject.user.MemberUserDO;
import com.hisun.kugga.module.member.service.user.MemberUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 会员用户的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MemberUserApiImpl implements MemberUserApi {

    @Resource
    private MemberUserService userService;

    @Override
    public UserRespDTO getUser(Long id) {
        MemberUserDO user = userService.getUser(id);
        return UserConvert.INSTANCE.convert2(user);
    }

}
