package com.hisun.kugga.duke.bos.controller.admin.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class DrawbackDetailRspBody {
    /**
     * 退款金额
     */
    private Integer drawbackAmount;
    /**
     * 退款发起时间
     */
    private String startTime;
    /**
     * 退款状态： draft 待分账；processing 处理中；partial_success 部分成功；full_success 已全部成功；failed 全部失败；
     */
    private String status;
    /**
     * 退款完成时间
     */
    private String successTime;
}
