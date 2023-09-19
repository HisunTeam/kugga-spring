package com.hisun.kugga.duke.user.api.oauth2;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.user.api.oauth2.dto.UserInfoRespDTO;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.duke.user.service.user.DukeUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/27 14:51
 */
@Service
public class DukeUserApiImpl implements DukeUserApi {

    @Resource
    private DukeUserService dukeUserService;

    /**
     * service 接口用户信息会缓存
     */
    @Override
    public UserInfoRespDTO getUserById(Long id) {
        UserDO user = dukeUserService.getUser(id);
        UserInfoRespDTO respDTO = new UserInfoRespDTO();
        BeanUtils.copyProperties(user, respDTO);
        return respDTO;
    }

    @Override
    public UserInfoRespDTO getUserByEmail(String mail) {
        UserDO user = dukeUserService.getUserByEmail(mail);
        if (ObjectUtil.isNull(user)) {
            return null;
        }
        UserInfoRespDTO respDTO = new UserInfoRespDTO();
        BeanUtils.copyProperties(user, respDTO);
        return respDTO;
    }
}
