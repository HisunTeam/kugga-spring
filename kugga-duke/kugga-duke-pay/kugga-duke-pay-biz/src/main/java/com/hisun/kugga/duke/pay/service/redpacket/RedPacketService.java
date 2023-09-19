package com.hisun.kugga.duke.pay.service.redpacket;

import com.hisun.kugga.duke.pay.api.order.dto.RedPacketApplyReqDTO;
import com.hisun.kugga.duke.pay.api.order.dto.RedPacketApplyRspDTO;
import com.hisun.kugga.duke.pay.controller.app.redpacket.vo.BonusCalculateRspVO;
import com.hisun.kugga.duke.vo.RedPacketDetailRspVO;

/**
 * @author: zhou_xiong
 */
public interface RedPacketService {
    /**
     * 红包分配计算
     *
     * @param leagueId
     * @return
     */
    BonusCalculateRspVO calculate(Long leagueId);

    /**
     * 发红包
     *
     * @param redPacketId
     * @return
     */
    RedPacketApplyRspDTO handout(Long redPacketId);

    /**
     * 发红包申请
     *
     * @param redPacketApplyReqDTO
     * @return
     */
    RedPacketApplyRspDTO redPacketApply(RedPacketApplyReqDTO redPacketApplyReqDTO);

    /**
     * 通过内部订单号查询红包发放详情
     *
     * @param appOrderNo
     * @param fromBatch
     * @return
     */
    RedPacketDetailRspVO getRedPacketDetail(String appOrderNo, boolean fromBatch);
}
