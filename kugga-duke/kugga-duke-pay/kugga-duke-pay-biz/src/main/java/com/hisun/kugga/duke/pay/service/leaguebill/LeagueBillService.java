package com.hisun.kugga.duke.pay.service.leaguebill;

import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageReqVO;
import com.hisun.kugga.duke.pay.controller.app.leaguebill.vo.LeagueBillPageRspVO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.RedPacketInfo;
import com.hisun.kugga.duke.pay.dal.dataobject.leaguebill.LeagueBillDO;
import com.hisun.kugga.framework.common.pojo.PageResult;

import java.util.List;

/**
 * 公会账单 Service 接口
 *
 * @author zhou_xiong
 */
public interface LeagueBillService {
    /**
     * 分页查询
     *
     * @param pageVO
     * @return
     */
    PageResult<LeagueBillPageRspVO> pageQuery(LeagueBillPageReqVO pageVO);

    /**
     * 如果订单没生成账单，就生成
     *
     * @param leagueBillDO
     */
    void insertIfNotExist(LeagueBillDO leagueBillDO);

    /**
     * 通过billId查询红包发放详情
     *
     * @param billId
     * @return
     */
    List<RedPacketInfo> redPacketDetail(Long leagueId, Long billId);
}
