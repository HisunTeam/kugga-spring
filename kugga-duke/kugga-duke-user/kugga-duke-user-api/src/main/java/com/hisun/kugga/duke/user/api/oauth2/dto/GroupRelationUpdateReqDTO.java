package com.hisun.kugga.duke.user.api.oauth2.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class GroupRelationUpdateReqDTO {

    /**
     * 收藏id
     */
    private Long favoriteId;

    /**
     * G-公会 T-推荐信 P-帖子
     */
    private String type;

    /**
     * 内容id 工会、推荐报告id..
     */
    private Long contentId;

    /**
     * 分组id列表
     */
    private List<Long> groupIds;

    /**
     * 收藏推荐报告时所在公会id
     */
    private Long favoriteLeagueId;
}
