package com.hisun.kugga.duke.bos.controller.admin.wallet.dto;

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
     * 分账记录编号
     */
    private String sharingNo;
}
