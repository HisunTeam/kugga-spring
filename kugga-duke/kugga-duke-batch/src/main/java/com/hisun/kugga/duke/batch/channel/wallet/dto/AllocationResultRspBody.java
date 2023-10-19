package com.hisun.kugga.duke.batch.channel.wallet.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class AllocationResultRspBody {
    private String profitSharingStatus;

    private List<Receiver> receiverList;
    /**
     * Transaction Record Number
     */
    private String sharingNo;
}
