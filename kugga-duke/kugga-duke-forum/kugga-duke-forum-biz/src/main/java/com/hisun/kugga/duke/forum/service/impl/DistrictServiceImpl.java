package com.hisun.kugga.duke.forum.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumDistrictDO;
import com.hisun.kugga.duke.forum.dal.mysql.ForumDistrictMapper;
import com.hisun.kugga.duke.forum.service.DistrictService;
import com.hisun.kugga.duke.forum.vo.DistrictsReqVO;
import com.hisun.kugga.duke.forum.vo.DistrictsRespVO;
import com.hisun.kugga.duke.forum.vo.ForumDistrictVO;
import com.hisun.kugga.duke.forum.vo.UpdateDistrictReqVO;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.system.api.params.SystemParamsApi;
import com.hisun.kugga.duke.system.api.params.dto.SystemParamsRespDTO;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author zuocheng
 */
@Slf4j
@Service
public class DistrictServiceImpl implements DistrictService {

    private final static String TYPE = "forum";

    private final static String DISTRICT_UPPER_LIMIT = "district_upper_limit";
    @Resource
    private LeagueApi leagueApi;

    @Resource
    private SystemParamsApi systemParamsApi;

    @Resource
    private ForumDistrictMapper forumDistrictMapper;

    /**
     * 创建分区
     *
     * @param reqVO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDistrict(UpdateDistrictReqVO reqVO) {
        if (0L == reqVO.getForumId().longValue()) {
            log.info("匿名论坛暂不支持创建论坛分区");
            return;
        }

        //目前默认只有公会论坛才可以创建论坛分区,因为需要检查用户管理员权限
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        if (!leagueApi.isLeagueAdmin(reqVO.getForumId(), userId)) {
            throw exception(BusinessErrorCodeConstants.LEAGUE_NOT_ADMIN);
        }

        //读取允许配置的分区上限数
        int ulNum = queryDistrictUpperLimit();

        //当前提交的分区数
        int updateNum = reqVO.getDistricts().size();

        //检查分区数是否达到了上限
        if (updateNum > ulNum) {
            log.info("分区创建数[{}]>上限分区数[{}],不允许创建新的分区", updateNum, ulNum);
            throw exception(BusinessErrorCodeConstants.DISTRICT_NUM_UPPER_LIMIT);
        }

        //批量插入分区
        List<ForumDistrictDO> insertBatch = new ArrayList<>(updateNum);
        reqVO.getDistricts().stream().forEach(item -> {
            //外围有传递ID的数据检查ID是否存在,不存在的不允许手动设置ID,避免恶意插入ID导致主键冲突
            if (ObjectUtil.isNotNull(item.getDistrictId())) {
                int countNum = forumDistrictMapper.countById(item.getDistrictId(), reqVO.getForumId()).intValue();
                //未查询到数据时,请求报文非法,直接报系统错
                if (countNum != 1) {
                    log.info("请求数据非法,id[{}]与论坛id[{}]在表中无匹配的数据,系统禁止用户自己设置ID,避免主键冲突"
                            , item.getDistrictId(), reqVO.getForumId());
                    throw exception(BusinessErrorCodeConstants.SYSTEM_ERROR);
                }
            }

            ForumDistrictDO insert = new ForumDistrictDO()
                    .setId(item.getDistrictId())
                    .setDistrictName(item.getDistrictName())
                    .setForumId(reqVO.getForumId())
                    .setCreateTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());

            insertBatch.add(insert);
        });

        //根据论坛ID,删除论坛的分区
        forumDistrictMapper.deleteByForumId(reqVO.getForumId());
        //插入新数据
        forumDistrictMapper.insertBatch(insertBatch);
    }

    @Override
    public DistrictsRespVO districts(DistrictsReqVO reqVO) {
        if (0L == reqVO.getForumId().longValue()) {
            log.info("匿名论坛暂不支持创建论坛分区");
            return null;
        }

        List<ForumDistrictDO> datum = forumDistrictMapper.selectForumDistrictsByForumId(reqVO.getForumId());
        //设置响应列表
        List<ForumDistrictVO> districts = new ArrayList<>(datum.size());
        datum.stream().forEach(item -> {
            ForumDistrictVO forumDistrictVO = new ForumDistrictVO()
                    .setDistrictId(item.getId())
                    .setDistrictName(item.getDistrictName());

            districts.add(forumDistrictVO);
        });

        //读取允许配置的分区上限数
        int ulNum = queryDistrictUpperLimit();
        //计算可创建数量
        int canCreateNum = ulNum - datum.size();
        if (canCreateNum <= 0) {
            canCreateNum = 0;
        }

        return new DistrictsRespVO()
                .setDistricts(districts)
                .setCanCreateNum(canCreateNum);
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
