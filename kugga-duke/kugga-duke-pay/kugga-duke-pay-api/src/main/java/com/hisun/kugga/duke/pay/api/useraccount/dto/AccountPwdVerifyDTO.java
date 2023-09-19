package com.hisun.kugga.duke.pay.api.useraccount.dto;

import com.hisun.kugga.duke.entity.VerifyBaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/7 13:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPwdVerifyDTO extends VerifyBaseVo {
    /**
     * 密码
     */
    private String password;
}
