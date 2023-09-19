package com.hisun.kugga.duke.pay.dal.dataobject.redpacket;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class BonusUserDO {
    private Long userId;
    private String firstName;
    private String lastName;
    private Long growthValue;
}
