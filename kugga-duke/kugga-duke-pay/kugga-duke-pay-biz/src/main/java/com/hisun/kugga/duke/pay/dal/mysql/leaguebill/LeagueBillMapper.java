package com.hisun.kugga.duke.pay.dal.mysql.leaguebill;

import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.RedPacketInfo;
import com.hisun.kugga.duke.pay.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 公会账单 Mapper
 *
 * @author zhou_xiong
 */
@Mapper
public interface LeagueBillMapper extends BaseMapperX<LeagueBillDO> {

    default PageResult<LeagueBillDO> selectPage(LeagueBillPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LeagueBillDO>()
                .eqIfPresent(LeagueBillDO::getLeagueId, reqVO.getLeagueId())
                .eq(LeagueBillDO::getDeleted, false)
                .orderByDesc(LeagueBillDO::getCreateTime)
                .select(LeagueBillDO::getCreateTime, LeagueBillDO::getRemark,
                        LeagueBillDO::getAmount, LeagueBillDO::getStatus,
                        LeagueBillDO::getId)
        );
    }

    /**
     * 账单-红包详情
     *
     * @return
     */
    List<RedPacketInfo> redPacketDetail(Long billId);
}
