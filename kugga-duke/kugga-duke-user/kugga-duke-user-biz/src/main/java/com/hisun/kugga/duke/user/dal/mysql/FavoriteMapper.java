package com.hisun.kugga.duke.user.dal.mysql;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteDeleteReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoritePageReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.dal.dataobject.FavoriteDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FavoriteMapper extends BaseMapperX<FavoriteDO> {


    /**
     * 分页查询收藏
     *
     * @param page
     * @param reqVO
     * @return
     */
    Page<FavoriteRespVO> selectLeaguePageByByCondition(@Param("page") Page<FavoriteRespVO> page, @Param("reqVO") FavoritePageReqVO reqVO);

    /**
     * 分页查询推荐信
     *
     * @param page
     * @param reqVO
     * @return
     */
    Page<FavoriteRespVO> selectRecommendationPageByByCondition(@Param("page") Page<FavoriteRespVO> page, @Param("reqVO") FavoritePageReqVO reqVO);

    /**
     * 在收藏里获取可认证的公会 公会认证
     * <p>
     * 公会必须要已被认证才能帮别人认证
     * 推荐信只需要要求公会已被认证，公会认证还需要公会规则开启认证
     *
     * @param page
     * @param reqVO
     * @return
     */
    Page<FavoriteRespVO> selectAuthLeagues(@Param("page") Page<FavoriteRespVO> page, @Param("reqVO") FavoritePageReqVO reqVO);

    /**
     * 在收藏里获取可认证的公会 推荐信
     *
     * @param page
     * @param reqVO
     * @return
     */
    Page<FavoriteRespVO> selectAuthLeaguesRecommendation(@Param("page") Page<FavoriteRespVO> page, @Param("reqVO") FavoritePageReqVO reqVO);


    /**
     * 查询当前用户加入的公会id
     *
     * @param userId
     * @return
     */
    List<Long> selectUserJoinLeagueIds(@Param("userId") Long userId);

    /**
     * 根据 用户id 内容id和类型删除
     *
     * @param reqVO
     */
    void deleteByUserIdAndContendIdAndType(@Param("reqVO") FavoriteDeleteReqVO reqVO);


    /**
     * 根据用户ID 与 收藏ID查询数据是否存在
     *
     * @param id
     * @param userId
     * @return
     */
    default FavoriteDO selectIdByContentTypeUserId( FavoriteDeleteReqVO reqVO) {
        return selectOne(new LambdaQueryWrapperX<FavoriteDO>()
                .eq(FavoriteDO::getContentId, reqVO.getContentId())
                .eq(FavoriteDO::getType, reqVO.getType())
                .eq(FavoriteDO::getUserId, reqVO.getUserId())
                .last("limit 1"));
    }


    /**
     * 通过推荐信id查询推荐信详情内容
     *
     * @param recommendationId
     * @return
     */
    List<String> getRecommendationContentByRecomId(@Param("recommendationId") Long recommendationId);

    /**
     * 根据用户ID 与 收藏ID查询数据是否存在
     *
     * @param id
     * @param userId
     * @return
     */
    default Long countById(long id, long userId,String type) {
        return selectCount(new LambdaQueryWrapperX<FavoriteDO>()
                .eq(FavoriteDO::getId, id)
                .eq(FavoriteDO::getType, type)
                .eq(FavoriteDO::getUserId, userId));
    }
}
