package com.hisun.kugga.duke.system.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 短链接绑定 DO
 *
 * @author 芋道源码
 */
@TableName("duke_short_url")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDO {

    /**
     * 短链ID
     */
    @TableId
    private Long id;
    /**
     * 短链类型:g:公会邀请短链（由前端固定页面获取参数）, r:默认短链(公共后台自己重定向)
     */
    private String linkType;
    /**
     * http/https链接
     */
    private String url;
    /**
     * uri
     */
    private String uri;
    /**
     * 短链码
     */
    private String shotCode;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 失效日期
     */
    private LocalDateTime expireTime;
    /**
     * 是否已失效: false:未失效 true:已失效
     */
    private Boolean expireFlag;
    /**
     * 连接次数
     */
    private Integer totalClickCount;

}
