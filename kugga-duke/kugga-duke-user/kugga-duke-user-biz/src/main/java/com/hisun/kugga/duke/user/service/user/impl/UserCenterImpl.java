package com.hisun.kugga.duke.user.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.user.common.UserRelationEnum;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoritePageReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserCenterReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserHomePageRespVo;
import com.hisun.kugga.duke.user.dal.dataobject.UserDO;
import com.hisun.kugga.duke.user.service.favorite.FavoriteService;
import com.hisun.kugga.duke.user.service.user.DukeUserService;
import com.hisun.kugga.duke.user.service.user.UserCenterService;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/27 15:55
 */
@Service
@Validated
public class UserCenterImpl implements UserCenterService {

    @Resource
    private DukeUserService dukeUserService;
    @Resource
    private FavoriteService favoriteService;

    @Override
    public UserHomePageRespVo getUserHomePage(Long userId, String type) {
        UserHomePageRespVo respVo = new UserHomePageRespVo();
        UserRelationEnum relation = UserRelationEnum.getEnumByCode(type);
        // 用户基本信息
        UserDO user = dukeUserService.getUser(userId);
        BeanUtils.copyProperties(user, respVo);
        //个人简历
        if (ObjectUtil.notEqual(relation, UserRelationEnum.SAME)) {

        }
        // 推荐报告

        return respVo;
    }

    @Override
    public PageResult<FavoriteRespVO> getFavorite(UserCenterReqVO reqVO) {
        FavoritePageReqVO pageReqVO = new FavoritePageReqVO();
        pageReqVO.setUserId(reqVO.getUserId());
        pageReqVO.setType(reqVO.getFavoriteType());
        pageReqVO.setPageNo(reqVO.getPageNo());
        pageReqVO.setPageSize(reqVO.getPageSize());
        return favoriteService.getFavoritePage(pageReqVO);
    }
}
