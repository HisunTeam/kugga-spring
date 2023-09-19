package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公会账单 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_bill")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueBillDO {

    /**
     * 交易id
     */
    @TableId
    private Long id;
    /**
     * 公会账单表
     */
    private String billNo;
    /**
     * 交易类型
     */
    private String type;
    /**
     * 交易人(用户ID)
     */
    private Long userId;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    /**
     * 交易状态: U-初始,S-成功,F-失败
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

}
