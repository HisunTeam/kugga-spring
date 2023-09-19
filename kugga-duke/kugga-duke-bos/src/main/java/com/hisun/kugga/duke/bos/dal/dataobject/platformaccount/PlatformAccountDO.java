package com.hisun.kugga.duke.bos.dal.dataobject.platformaccount;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 公会 DO
 *
 * @author lzt
 */
@TableName("duke_platform_account")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAccountDO extends BaseDO {

    /**
     * 账户ID
     */
    @TableId
    private Long id;
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 外部钱包账户编号
     */
    private String accountId;
    /**
     * 账户金额
     */
    private BigDecimal balance;

}
