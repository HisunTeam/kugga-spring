package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class RedPacketDetailRspBody {
    private String completeTime;
    private String orderNo;
    private String status;
    private String remark;
}
