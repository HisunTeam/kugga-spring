package com.hisun.kugga.duke.forum.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.forum.dal.dataobject.ForumPlateDO;
import com.hisun.kugga.duke.forum.dal.mysql.ForumPlateMapper;
import com.hisun.kugga.duke.forum.enums.PlateEnums;
import com.hisun.kugga.duke.forum.service.PlateService;
import com.hisun.kugga.duke.forum.vo.PagePlateReqVO;
import com.hisun.kugga.duke.forum.vo.PlateReqVO;
import com.hisun.kugga.duke.forum.vo.PlateVO;
import com.hisun.kugga.duke.forum.vo.SimplePlateVO;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author zuocheng
 */
@Service
@Slf4j
public class PlateServiceImpl implements PlateService {

    @Resource
    private ForumPlateMapper forumPlateMapper;

    @Resource
    private LeagueApi leagueApi;

    @Override
    public PageResult<PlateVO> pageList(PagePlateReqVO reqVO) {
        PageResult<ForumPlateDO> pagePlates = forumPlateMapper.selectPlates(reqVO, "en-US");

        //ForumPlateDO转PlateVO
        List<PlateVO> list = new ArrayList<>(pagePlates.getList().size());
        pagePlates.getList().stream().forEach(item -> {
            PlateVO plate = new PlateVO();
            BeanUtils.copyProperties(item, plate);
            list.add(plate);
        });

        return new PageResult<PlateVO>().setList(list).setTotal(pagePlates.getTotal());
    }

    @Override
    public SimplePlateVO simple(PlateReqVO reqVO) {
        SimplePlateVO rsp = new SimplePlateVO();
        if (PlateEnums.PLATE_1.getType().equals(reqVO.getPlate())) {
            //匿名板块直接查询板块配置表
            ForumPlateDO plate = forumPlateMapper.selectOneByValue(PlateEnums.PLATE_1.getType(), "en-US");
            rsp.setPlateName(plate.getPlateName());
            rsp.setPlateAvatar(plate.getPlateAvatar());
            rsp.setVisitAuth(true);
            rsp.setUserJoinPrice(new BigDecimal("0"));
            rsp.setEnabledAdminApproval(false);
            rsp.setEnabledUserJoin(false);
            rsp.setAdminFlag(false);
        } else {
            //目前非匿名板块,则必为公会取公会信息
            rsp = forumPlateMapper.selectLeague(reqVO.getGroupId());
            if (ObjectUtil.isNull(rsp)) {
                throw exception(BusinessErrorCodeConstants.INFO_NOT_EXIST);
            }
            Long userId = SecurityFrameworkUtils.getLoginUserId();
            if (ObjectUtil.isNull(userId)) {
                rsp.setVisitAuth(false);
                rsp.setAdminFlag(false);
            } else {
                rsp.setVisitAuth(leagueApi.isLeagueMember(reqVO.getGroupId(), userId));
                rsp.setAdminFlag(leagueApi.isLeagueAdmin(reqVO.getGroupId(), userId));
            }
        }

        return rsp;
    }
}
