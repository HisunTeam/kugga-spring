package com.hisun.kugga.duke.user.service.favorite;

import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteDeleteReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoritePageReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.dal.dataobject.FavoriteDO;
import com.hisun.kugga.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 收藏 Service 接口
 *
 * @author 芋道源码
 */
public interface FavoriteService {

    /**
     * 创建收藏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFavorite(@Valid FavoriteCreateReqVO createReqVO);

    /**
     * 删除收藏
     *
     * @param reqVO 编号
     */
    void deleteFavorite(FavoriteDeleteReqVO reqVO);

    /**
     * 获得收藏
     *
     * @param id 编号
     * @return 收藏
     */
    FavoriteDO getFavorite(Long id);

    /**
     * 获得收藏列表
     *
     * @param ids 编号
     * @return 收藏列表
     */
    List<FavoriteDO> getFavoriteList(Collection<Long> ids);

    /**
     * 获得收藏分页
     *
     * @param pageReqVO 分页查询
     * @return 收藏分页
     */
    PageResult<FavoriteRespVO> getFavoritePage(FavoritePageReqVO pageReqVO);

    /**
     * 在收藏里获取可认证的公会
     *
     * @param pageVO
     * @return
     */
    PageResult<FavoriteRespVO> getAuthLeagues(FavoritePageReqVO pageVO);
}
