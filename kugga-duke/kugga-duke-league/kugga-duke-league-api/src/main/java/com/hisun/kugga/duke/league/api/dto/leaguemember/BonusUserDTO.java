package com.hisun.kugga.duke.league.api.dto.leaguemember;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class BonusUserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private Long growthValue;
}
