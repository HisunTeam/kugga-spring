package com.hisun.kugga.duke.user.service.favoritegroup;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.system.api.params.SystemParamsApi;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.duke.user.common.FavoriteEnum;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupExportReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupRespVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupUpdateReqVO;
import com.hisun.kugga.duke.user.controller.app.favoritegroup.vo.FavoriteGroupVO;
import com.hisun.kugga.duke.user.dal.dataobject.favoritegroup.FavoriteGroupDO;
import com.hisun.kugga.duke.user.dal.mysql.favoritegroup.FavoriteGroupMapper;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 收藏分组 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class FavoriteGroupServiceImpl implements FavoriteGroupService {
    private final static String TYPE = "group";

    private final static String DISTRICT_UPPER_LIMIT = "group_upper_limit";
    @Resource
    private FavoriteGroupMapper favoriteGroupMapper;

    @Resource
    private SystemParamsApi systemParamsApi;

    /**
     * 创建分组
     *
     * @param reqVO
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateFavoriteGroup(FavoriteGroupUpdateReqVO reqVO) {

        //检查当前用户
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (ObjectUtil.isNull(userId)) {
            throw exception(BusinessErrorCodeConstants.USER_ACCOUNT_NOT_EXIST);
        }

        //读取允许配置的分区上限数
        int ulNum = queryDistrictUpperLimit();

        //当前提交的分区数
        int updateNum = reqVO.getGroups().size();

        //检查分区数是否达到了上限
        if (updateNum > ulNum) {
            log.info("分组创建数[{}]>上限分区数[{}],不允许创建新的分组", updateNum, ulNum);
            throw exception(BusinessErrorCodeConstants.GROUP_NUM_UPPER_LIMIT);
        }
        FavoriteEnum type = FavoriteEnum.getEnumByCode(reqVO.getType());
        //批量插入分区，先批量逻辑删除
        deleteFavoriteGroupBatch(userId, type.getCode());
        reqVO.getGroups().stream().forEach(item -> {
            FavoriteGroupDO favoriteGroupDO = new FavoriteGroupDO()
                    .setId(item.getId())
                    .setUserId(userId)
                    .setGroupName(item.getGroupName())
                    .setType(type.getCode());

            if (ObjectUtil.isNotNull(item.getId())) {
                // 校验存在
                this.validateFavoriteGroupExists(item.getId(), userId, type.getCode());
                favoriteGroupDO.setDeleted(false);
                favoriteGroupMapper.updateGroupById(favoriteGroupDO);
            } else {
                favoriteGroupMapper.insert(favoriteGroupDO);
            }
        });

    }

    @Override
    public void deleteFavoriteGroup(Long id, String type) {

        Long userId = SecurityFrameworkUtils.getLoginUserId();

        // 校验存在
        this.validateFavoriteGroupExists(id, userId, type);
        // 删除
        favoriteGroupMapper.deleteById(id);
    }

    private void deleteFavoriteGroupBatch(Long userId, String type) {

        // 删除
        favoriteGroupMapper.deleteByUserId(userId, type);
    }

    @Override
    public void validateFavoriteGroupExists(Long id, Long userId, String type) {
        if (favoriteGroupMapper.countByIdTypeUserId(id, userId, type) == 0) {
            log.info("请求数据非法,id[{}]与用户id[{}]在表中无匹配的数据,系统禁止用户自己设置ID,避免主键冲突"
                    , id, userId);
            throw exception(BusinessErrorCodeConstants.FAVORITE_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public FavoriteGroupRespVO getFavoriteGroupList(FavoriteGroupExportReqVO reqVO) {

        //检查当前用户
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (ObjectUtil.isNull(userId)) {
            throw exception(BusinessErrorCodeConstants.USER_ACCOUNT_NOT_EXIST);
        }
        FavoriteEnum type = FavoriteEnum.getEnumByCode(reqVO.getType());


        List<FavoriteGroupDO> datum = favoriteGroupMapper.selectGroupByUserId(userId, type.getCode());
        //设置响应列表
        List<FavoriteGroupVO> districts = new ArrayList<>(datum.size());
        datum.stream().forEach(item -> {
            FavoriteGroupVO forumDistrictVO = new FavoriteGroupVO()
                    .setId(item.getId())
                    .setGroupName(item.getGroupName());

            districts.add(forumDistrictVO);
        });

        //读取允许配置的分区上限数
        int ulNum = queryDistrictUpperLimit();
        //计算可创建数量
        int canCreateNum = ulNum - datum.size();
        if (canCreateNum <= 0) {
            canCreateNum = 0;
        }

        return new FavoriteGroupRespVO()
                .setGroups(districts)
                .setCanCreateNum(canCreateNum)
                .setType(type.getCode());
    }

    /**
     * 查询分区上限值
     *
     * @return
     */
    private int queryDistrictUpperLimit() {
        //目前有分区上线要求,先获取系统配置的上限数,在查询当前论坛已创建分区数,如果已超过上限,则不允许添加新的分区
        SystemParamsRespDTO upperLimit = systemParamsApi.getSystemParams(TYPE, DISTRICT_UPPER_LIMIT);
        //判断upperLimit是否为空,此参数必配置,弱为空直接报系统错误
        if (ObjectUtil.isNull(upperLimit)) {
            log.info("未在duke_system_params表中配置分区上限值");
            throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
        }

        if (StrUtil.isBlank(upperLimit.getValue())) {
            log.info("在duke_system_params表中配置分区上限值为空,无法创建分区");
            throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
        }

        //上限数
        int ulNum = 0;
        try {
            ulNum = Integer.parseInt(upperLimit.getValue());
        } catch (NumberFormatException e) {
            log.info("在duke_system_params表中配置分区上限值为[{}]配置错误,因配置成整数", upperLimit.getValue());
            throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
        }

        return ulNum;
    }

}
