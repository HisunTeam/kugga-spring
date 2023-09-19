package com.hisun.kugga.duke.user.service.favorite;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.user.common.FavoriteEnum;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteCreateReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteDeleteReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoritePageReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteRespVO;
import com.hisun.kugga.duke.user.dal.dataobject.FavoriteDO;
import com.hisun.kugga.duke.user.dal.mysql.FavoriteMapper;
import com.hisun.kugga.duke.user.service.favoritegrouprelation.FavoriteGroupRelationService;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 收藏 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private FavoriteGroupRelationService favoriteGroupRelationService;

    @Override
    public Long createFavorite(FavoriteCreateReqVO createReqVO) {
        FavoriteDO favorite = new FavoriteDO();
        //收藏推荐信时公会id不能为空
        if (ObjectUtil.equal(createReqVO.getType(), FavoriteEnum.RECOMMENDATION.getCode()) &&
                ObjectUtil.isNull(createReqVO.getFavoriteLeagueId())) {
            throw exception(FAVORITE_RECOMMENDATION_LEAGUE_NOT_NULL);
        }
        try {
            BeanUtils.copyProperties(createReqVO, favorite);
            int res = favoriteMapper.insert(favorite);
            if (res != 1) {
                throw exception(DB_INSERT_FAILED);
            }
        } catch (DuplicateKeyException e) {
            throw exception(FAVORITE_EXISTS);
        } catch (Exception e) {
            throw exception(DB_INSERT_FAILED);
        }
        return favorite.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteFavorite(FavoriteDeleteReqVO reqVO) {
        // 删除
        //检查当前用户
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        reqVO.setUserId(userId);
        FavoriteDO favoriteDO = favoriteMapper.selectIdByContentTypeUserId(reqVO);
        favoriteMapper.deleteByUserIdAndContendIdAndType(reqVO);
        favoriteGroupRelationService.deleteFavoriteRelation(favoriteDO.getId(),favoriteDO.getType());
    }

    private void validateFavoriteExists(Long id) {
        if (favoriteMapper.selectById(id) == null) {
            throw exception(FAVORITE_NOT_EXISTS);
        }
    }

    @Override
    public FavoriteDO getFavorite(Long id) {
        return favoriteMapper.selectById(id);
    }

    @Override
    public List<FavoriteDO> getFavoriteList(Collection<Long> ids) {
        return favoriteMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FavoriteRespVO> getFavoritePage(FavoritePageReqVO reqVO) {
        Page<FavoriteRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        Page<FavoriteRespVO> resPage = new Page<>();
        FavoriteEnum type = FavoriteEnum.getEnumByCode(reqVO.getType());
        if (ObjectUtil.equal(type, FavoriteEnum.LEAGUE)) {
            resPage = favoriteMapper.selectLeaguePageByByCondition(page, reqVO);
        } else if (ObjectUtil.equal(type, FavoriteEnum.RECOMMENDATION)) {
            resPage = favoriteMapper.selectRecommendationPageByByCondition(page, reqVO);
            // 推荐信内容填充
            if (ObjectUtil.isNotNull(resPage) && ObjectUtil.isNotEmpty(resPage.getRecords())) {
                for (FavoriteRespVO record : resPage.getRecords()) {
                    List<String> contents = favoriteMapper.getRecommendationContentByRecomId(record.getContentId());
                    List<Content> contentData = contents.stream()
                            .map(content -> JSONUtil.toBean(content, Content.class))
                            .collect(Collectors.toList());
                    record.setContents(contentData);
                }
            }
        } else {
            throw exception(PARAM_ERROR);
        }

        return new PageResult<>(resPage.getRecords(), resPage.getTotal());
    }

    @Override
    public PageResult<FavoriteRespVO> getAuthLeagues(FavoritePageReqVO reqVO) {
        /*
        1.推荐报告 收藏已加入的工会价格为0
        2.公会必须要已被认证才能帮别人认证
        3、推荐信只需要要求公会已被认证，公会认证还需要公会规则开启认证

        authType G 公会认证，里面公会认证需要开启公会认证，价格是认证价格 已加入的公会认证价格不变
                 T 公会推荐信，里面公会只需要已被认证，价格是推荐信价格 已加入的公会推荐信价格为0
         */
        Page<FavoriteRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());

        Page<FavoriteRespVO> resPage = new Page<>();
        FavoriteEnum type = FavoriteEnum.getEnumByCode(reqVO.getAuthType());
        if (ObjectUtil.equal(type, FavoriteEnum.LEAGUE)) {
            resPage = favoriteMapper.selectAuthLeagues(page, reqVO);
        } else if (ObjectUtil.equal(type, FavoriteEnum.RECOMMENDATION)) {
            resPage = favoriteMapper.selectAuthLeaguesRecommendation(page, reqVO);
            List<FavoriteRespVO> records = resPage.getRecords();
            if (ObjectUtil.isNotEmpty(records)) {
                // 推荐信认证公会列表里里 收藏已加入的工会价格为0
                List<Long> leagueIds = favoriteMapper.selectUserJoinLeagueIds(reqVO.getUserId());
                if (ObjectUtil.isNotEmpty(leagueIds)) {
                    records.stream()
                            .filter(item -> leagueIds.contains(item.getContentId()))
                            .forEach(item -> item.setPrice(BigDecimal.ZERO));
                }
            }
        } else {
            throw exception(PARAM_ERROR);
        }
        return new PageResult<>(resPage.getRecords(), resPage.getTotal());
    }

}
