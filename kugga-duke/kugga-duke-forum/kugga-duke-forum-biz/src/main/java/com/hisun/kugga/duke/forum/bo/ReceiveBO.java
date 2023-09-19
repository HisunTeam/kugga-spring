package com.hisun.kugga.duke.forum.bo;

import lombok.Data;

/**
 * 被回复信息
 *
 * @author zuocheng
 */
@Data
public class ReceiveBO {
    /**
     * 被回复的消息ID
     */
    private Long id;
    /**
     * 被回复消息对应的贴子ID
     */
    private Long postsId;
    /**
     * 被回复消息的楼层ID
     */
    private Long floorId;
    /**
     * 被回复消息所属用户ID
     */
    private Long userId;
}
