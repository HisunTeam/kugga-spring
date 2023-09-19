package com.hisun.kugga.duke.chat.socket.constant;

public class SocketEvent {

    /**
     * 初始化
     */
    public final static String INIT = "init";
    /**
     * 卸载时
     */
    public final static String UNLOAD = "unload";
    /**
     * 从工会页面进入，请求发起聊天
     */
    public final static String REQUEST_CHAT = "request_chat";
    /**
     * 发送消息前预处理
     */
    public final static String BEFORE_MESSAGE = "before_message";
    /**
     * 接收到消息时
     */
    public final static String MESSAGE = "message";
    /**
     * 接收到消息时,在对话框显示已读
     */
    public final static String READ_MESSAGE = "read_message";
    /**
     * 收到撤回消息
     */
    public final static String REVOKE_MESSAGE = "revoke_message";
    /**
     * 好友申请
     */
    public final static String FRIEND_REQUEST = "friend_request";
    /**
     * 同意好友申请
     */
    public final static String FRIEND_REQUEST_AGREE = "friend_request_agree";
    /**
     * 拒绝好友申请
     */
    public final static String FRIEND_REQUEST_REJECT = "friend_request_reject";
    /**
     * 创建聊天室
     */
    public final static String CREATE_CHATROOM = "create_chatroom";
    /**
     * 邀请好友入群
     */
    public final static String INVITE_JOIN_CHATROOM = "invite_join_chatroom";
    /**
     * 聊天申请（加群申请）
     */
    public final static String CHAT_REQUEST = "chat_request";
    /**
     * 同意加群申请
     */
    public final static String CHAT_REQUEST_AGREE = "chat_request_agree";
    /**
     * 拒绝加群申请
     */
    public final static String CHAT_REQUEST_REJECT = "chat_request_reject";
    /**
     * RTC 相关数据
     */
    public final static String RTC_DATA = "rtc_data";
    /**
     * RTC 呼叫
     */
    public final static String RTC_CALL = "rtc_call";
    /**
     * RTC 挂断
     */
    public final static String RTC_HANG_UP = "rtc_hang_up";
    /**
     * RTC 繁忙
     */
    public final static String RTC_BUSY = "rtc_busy";

}
