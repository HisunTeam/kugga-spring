package com.hisun.kugga.duke.system.api.shorturl.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zuocheng
 */
@Data
public class CreateShortUrlRspDTO {
    /**
     * 短链ID
     */
    private Long id;
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
    /**
     * 短链接
     */
    private String shortUrl;
}
