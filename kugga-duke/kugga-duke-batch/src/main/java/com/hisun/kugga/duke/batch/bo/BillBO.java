package com.hisun.kugga.duke.batch.bo;

import com.hisun.kugga.duke.enums.AccountType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zhou_xiong
 */
@Data
public class BillBO {
    private String walletOrderNo;
    private AccountType accountType;
    private Long objectId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String remark;
}
