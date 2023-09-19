package com.hisun.kugga.duke.forum.bo;

import lombok.Data;

/**
 * 上升热贴
 *
 * @author zuocheng
 */
@Data
public class RisingHotPostsBO {
    /**
     * 贴子ID
     */
    private Long postsId;
    /**
     * 发生时间
     */
    private Long eventTime;
}
