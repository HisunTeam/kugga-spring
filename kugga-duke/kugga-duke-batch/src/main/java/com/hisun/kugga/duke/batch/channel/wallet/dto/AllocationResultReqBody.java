package com.hisun.kugga.duke.batch.channel.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class AllocationResultReqBody extends WalletBaseReqBody {
    @NotEmpty(message = "sharingNo cannot be empty")
    private String sharingNo;
}
