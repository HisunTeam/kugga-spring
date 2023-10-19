package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class DrawbackDetailRspBody {
    /**
     * Refund amount
     */
    private Integer drawbackAmount;
    /**
     * Refund initiation time
     */
    private String startTime;
    /**
     * Refund status: draft (Pending Allocation); processing (Processing); partial_success (Partial Success); full_success (All Successful); failed (All Failed);
     */
    private String status;
    /**
     * Refund completion time
     */
    private String successTime;
}

