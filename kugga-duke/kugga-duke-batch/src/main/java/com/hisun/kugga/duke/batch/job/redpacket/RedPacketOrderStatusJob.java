package com.hisun.kugga.duke.batch.job.redpacket;

import com.hisun.kugga.duke.batch.dal.dataobject.redpacketorder.RedPacketOrderDO;
import com.hisun.kugga.duke.batch.dal.mysql.redpacketorder.RedPacketOrderMapper;
import com.hisun.kugga.duke.dto.RedPacketDetailReqDTO;
import com.hisun.kugga.duke.innercall.InnerCallHelper;
import com.hisun.kugga.duke.vo.RedPacketDetailRspVO;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.quartz.core.handler.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * 红包发放结果定时轮询
 *
 * @author: zhou_xiong
 */
@Slf4j
@Component
public class RedPacketOrderStatusJob implements JobHandler {
    @Value("${duke.league.backed.redPacketDetail:}")
    private String detailUrl;
    @Resource
    private RedPacketOrderMapper redPacketOrderMapper;
    @Resource
    private InnerCallHelper innerCallHelper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String execute(String param) throws Exception {
        // 游标查询数据库中所有draft状态红包发放订单号
        Cursor<RedPacketOrderDO> selectCursor = redPacketOrderMapper.selectDraftOrders();
        try {
            Iterator<RedPacketOrderDO> iterator = selectCursor.iterator();
            while (iterator.hasNext()) {
                RedPacketOrderDO redPacketOrderDO = iterator.next();
                // 调用duke应用查询红包状态进行处理
                RedPacketDetailReqDTO redPacketDetailReqDTO = new RedPacketDetailReqDTO();
                redPacketDetailReqDTO.setAppOrderNo(redPacketOrderDO.getAppOrderNo());
                redPacketDetailReqDTO.setUuid(innerCallHelper.genCert(redPacketOrderDO.getAppOrderNo()));
                innerCallHelper.post(detailUrl, redPacketDetailReqDTO, RedPacketDetailRspVO.class);
            }
        } catch (Exception e) {
            log.error("RedPacketOrderStatusJob.execute() error", e);
        } finally {
            selectCursor.close();
        }
        return GlobalErrorCodeConstants.SUCCESS.getMsg();
    }

}
