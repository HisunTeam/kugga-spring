package com.hisun.kugga.duke.user.service.user;

import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserCenterReqVO;
import com.hisun.kugga.duke.user.controller.vo.user.UserHomePageRespVo;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * @Description: 用户中心
 * @author： Lin
 * @Date 2022/7/27 15:21
 */
public interface UserCenterService {

    /**
     * 获取个人主页
     *
     * @param userId 用户id
     * @param type   0同公会 1不同公会
     * @return
     */
    UserHomePageRespVo getUserHomePage(Long userId, String type);

    /**
     * 获取个人收藏
     *
     * @param reqVO
     * @return
     */
    PageResult<FavoriteRespVO> getFavorite(UserCenterReqVO reqVO);
}
