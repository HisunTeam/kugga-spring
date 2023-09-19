package com.hisun.kugga.duke.pay.dal.dataobject.redpacket;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.RedPacketStatus;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 红包任务 DO
 *
 * @author 芋道源码
 */
@TableName("duke_red_packet")
@KeySequence("duke_red_packet_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedPacketDO extends BaseDO {

    /**
     * 红包id
     */
    @TableId
    private Long id;
    /**
     * 发放用户id
     */
    private Long userId;
    /**
     * 发放公会
     */
    private Long leagueId;
    /**
     * 红包总金额
     */
    private BigDecimal amount;
    /**
     * 内部订单号
     */
    private String appOrderNo;
    /**
     * 红包分配参数
     */
    private String redPacketParam;
    /**
     * 红包状态：0-初始化；1-已下单；2-成功；3-失败
     */
    private RedPacketStatus businessStatus;

}
