package com.hisun.kugga.duke.user.cv.api.recommendation.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class RecommendationPageRspDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 名
     */
    private String lastName;
    /**
     * 姓
     */
    private String firstName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 推荐报告ID
     */
    private Long recommendationId;
    /**
     * 推荐报告内容
     */
    private String content;
    /**
     * 分享链接
     */
    private String shareLink;
}
