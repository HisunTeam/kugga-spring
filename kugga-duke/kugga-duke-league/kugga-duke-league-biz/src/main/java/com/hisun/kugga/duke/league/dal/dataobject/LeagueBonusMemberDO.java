package com.hisun.kugga.duke.league.dal.dataobject;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class LeagueBonusMemberDO {
    private Long userId;
    private Long growthValue;
    private String firstName;
    private String lastName;
}
