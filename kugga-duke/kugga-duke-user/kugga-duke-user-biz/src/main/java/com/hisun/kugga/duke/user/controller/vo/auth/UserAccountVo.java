package com.hisun.kugga.duke.user.controller.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/8/24 11:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 外部钱包账户编号
     */
    private String accountId;
    /**
     * 账户余额
     */
    private BigDecimal balance;
}
