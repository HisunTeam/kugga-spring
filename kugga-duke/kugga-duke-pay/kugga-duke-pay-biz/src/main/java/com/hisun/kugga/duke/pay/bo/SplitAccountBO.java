package com.hisun.kugga.duke.pay.bo;

import com.hisun.kugga.duke.pay.api.wallet.dto.Receiver;
import lombok.Data;

import java.util.List;

/**
 * @author: zhou_xiong
 */
@Data
public class SplitAccountBO {
    /**
     * 钱包订单号
     */
    private String walletOrderNo;
    /**
     * 收款人信息
     */
    List<Receiver> receiverList;
    /**
     * 分账订单id集合
     */
    List<Long> orderSubIds;
}
