package com.hisun.kugga.duke.user.service.favoritegrouprelation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.user.common.FavoriteEnum;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.DeleteRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.FavoriteRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo.GroupRelationUpdateReqVO;
import com.hisun.kugga.duke.user.controller.vo.favorite.FavoriteCreateReqVO;
import com.hisun.kugga.duke.user.dal.dataobject.FavoriteDO;
import com.hisun.kugga.duke.user.dal.dataobject.favoritegrouprelation.FavoriteGroupRelationDO;
import com.hisun.kugga.duke.user.dal.mysql.FavoriteMapper;
import com.hisun.kugga.duke.user.dal.mysql.favoritegroup.FavoriteGroupMapper;
import com.hisun.kugga.duke.user.dal.mysql.favoritegrouprelation.FavoriteGroupRelationMapper;
import com.hisun.kugga.duke.user.service.favoritegroup.FavoriteGroupService;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 收藏分组关联 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class FavoriteGroupRelationServiceImpl implements FavoriteGroupRelationService {

    @Resource
    private FavoriteGroupRelationMapper favoriteGroupRelationMapper;
    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private FavoriteGroupMapper favoriteGroupMapper;
    @Resource
    private FavoriteGroupService favoriteGroupService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateGroupRelation(GroupRelationUpdateReqVO updateReqVO) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        favoriteGroupRelationMapper.deleteByFavoriteId(updateReqVO.getFavoriteId(), updateReqVO.getType());
        if (CollUtil.isEmpty(updateReqVO.getGroupIds())) {
            log.info("未选择分组,不做收藏分组");
            return;
        }
        List<FavoriteGroupRelationDO> relationDOList = new ArrayList<>();
        if (ObjectUtil.isNull(updateReqVO.getFavoriteId())) {
            FavoriteCreateReqVO favoriteCreateReqVO = new FavoriteCreateReqVO();
            favoriteCreateReqVO.setType(updateReqVO.getType()).setContentId(updateReqVO.getContentId());
            FavoriteDO favorite = new FavoriteDO();
            //收藏推荐信时公会id不能为空
            if (ObjectUtil.equal(updateReqVO.getType(), FavoriteEnum.RECOMMENDATION.getCode()) &&
                    ObjectUtil.isNull(updateReqVO.getFavoriteLeagueId())) {
                throw exception(FAVORITE_RECOMMENDATION_LEAGUE_NOT_NULL);
            }
            try {
                BeanUtils.copyProperties(updateReqVO, favorite);
                favorite.setUserId(SecurityFrameworkUtils.getLoginUserId());
                int res = favoriteMapper.insert(favorite);
                if (res != 1) {
                    throw exception(DB_INSERT_FAILED);
                }
            } catch (DuplicateKeyException e) {
                throw exception(FAVORITE_EXISTS);
            } catch (Exception e) {
                throw exception(DB_INSERT_FAILED);
            }
            updateReqVO.setFavoriteId(favorite.getId());
        }

        updateReqVO.getGroupIds().stream().forEach(item -> {
            String type = FavoriteEnum.getEnumByCode(updateReqVO.getType()).getCode();
            if (item.equals(0L)) {
                return;
            }
            favoriteGroupService.validateFavoriteGroupExists(item, userId, type);
            relationDOList.add(new FavoriteGroupRelationDO()
                    .setGroupId(item)
                    .setType(type)
                    .setFavoriteId(updateReqVO.getFavoriteId()));
        });
        if (relationDOList.size() > 0) {
            favoriteGroupRelationMapper.insertBatch(relationDOList);
        }

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateFavoriteRelation(FavoriteRelationUpdateReqVO updateReqVO) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (ObjectUtil.isNotNull(updateReqVO.getGroupId())) {
            int countNum = favoriteGroupMapper.countById(updateReqVO.getGroupId(), userId, updateReqVO.getType()).intValue();
            //未查询到数据时,请求报文非法,直接报系统错
            if (countNum == 0) {
                log.info("请求数据非法,收藏分组id[{}]与用户id[{}]在表中无匹配的数据"
                        , updateReqVO.getGroupId(), userId);
                throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
            }
        }
        if (ObjectUtil.isNotNull(updateReqVO.getFavoriteIds())) {
            if (updateReqVO.getOldGroupId() == 0) {
                updateReqVO.getFavoriteIds().stream().forEach(item -> {
                    List<FavoriteGroupRelationDO> list = favoriteGroupRelationMapper.selectByFavoriteIdGroupId(item, updateReqVO.getGroupId(), updateReqVO.getType());
                    if(CollUtil.isNotEmpty(list)){
                        //如果已经收藏已经在当前分组里时,不在做分组操作,跳过执行下一条记录
                        return;
                    }
                    FavoriteGroupRelationDO favoriteGroupRelationDO = new FavoriteGroupRelationDO()
                            .setGroupId(updateReqVO.getGroupId())
                            .setType(FavoriteEnum.getEnumByCode(updateReqVO.getType()).getCode())
                            .setFavoriteId(item);
                    favoriteGroupRelationMapper.insert(favoriteGroupRelationDO);
                });
            } else {
                updateReqVO.getFavoriteIds().stream().forEach(item -> {
                    List<FavoriteGroupRelationDO> list = favoriteGroupRelationMapper.selectByFavoriteIdGroupId(item, updateReqVO.getGroupId(), updateReqVO.getType());
                    if(CollUtil.isNotEmpty(list)){
                        //如果已经收藏已经在当前分组里时,不在做分组操作,跳过执行下一条记录
                        return;
                    }
                    FavoriteGroupRelationDO favoriteGroupRelationDO = new FavoriteGroupRelationDO()
                            .setGroupId(updateReqVO.getGroupId())
                            .setType(FavoriteEnum.getEnumByCode(updateReqVO.getType()).getCode())
                            .setFavoriteId(item);
                    favoriteGroupRelationMapper.updateByGroupIdFavoriteId(favoriteGroupRelationDO, updateReqVO.getOldGroupId());
                });
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteFavoriteRelation(DeleteRelationUpdateReqVO updateReqVO) {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (ObjectUtil.isNotNull(updateReqVO.getType())) {
            if (ObjectUtil.isNotNull(updateReqVO.getFavoriteIds())) {
                updateReqVO.getFavoriteIds().stream().forEach(favoriteId -> {
                    int countNum = favoriteMapper.countById(favoriteId, userId, updateReqVO.getType()).intValue();
                    //未查询到数据时,请求报文非法,直接报系统错
                    if (countNum == 0) {
                        log.info("请求数据非法,收藏分类[{}]与用户id[{}]在表中无匹配的数据"
                                , updateReqVO.getType(), userId);
                        throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
                    }
                    favoriteMapper.deleteById(favoriteId);
                    favoriteGroupRelationMapper.deleteByFavoriteId(favoriteId, updateReqVO.getType());
                });

            }

        }


    }

    @Override
    public void deleteFavoriteRelation(Long favoriteId, String type) {
        favoriteGroupRelationMapper.deleteByFavoriteId(favoriteId, type);
    }

}
