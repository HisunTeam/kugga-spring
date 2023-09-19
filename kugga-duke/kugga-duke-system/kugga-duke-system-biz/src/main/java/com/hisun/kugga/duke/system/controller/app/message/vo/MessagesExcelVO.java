//package com.hisun.kugga.module.duke.controller.admin.messages.vo;
//
//import lombok.*;
//import java.util.*;
//import io.swagger.annotations.*;
//
//import com.alibaba.excel.annotation.ExcelProperty;
//
///**
// * 消息 Excel VO
// *
// * @author 芋道源码
// */
//@Data
//public class MessagesExcelVO {
//
//    @ExcelProperty("id")
//    private Long id;
//
//    @ExcelProperty("链路id 邀请和回调")
//    private String linkId;
//
//    @ExcelProperty("场景 (推荐信、聊天、认证、邀请加入公会、系统通知)")
//    private String scene;
//
//    @ExcelProperty("状态(邀请、回调)")
//    private String type;
//
//    @ExcelProperty("通知对象(个人 公会)")
//    private String target;
//
//    @ExcelProperty("业务id (认证id、推荐信id..)")
//    private Long businessId;
//
//    @ExcelProperty("业务链接")
//    private String businessLink;
//
//    @ExcelProperty("发起者")
//    private Long initiatorId;
//
//    @ExcelProperty("接收者")
//    private Long receiverId;
//
//    @ExcelProperty("发起者公会id")
//    private Long initiatorLeagueId;
//
//    @ExcelProperty("接收者公会id")
//    private Long receiverLeagueId;
//
//    @ExcelProperty("发起者姓")
//    private String initiatorLastname;
//
//    @ExcelProperty("发起者名")
//    private String initiatorFirstname;
//
//    @ExcelProperty("发起者99duke*")
//    private String initiatorUsername;
//
//    @ExcelProperty("接收者姓")
//    private String receiverLastname;
//
//    @ExcelProperty("接收者名")
//    private String receiverFirstname;
//
//    @ExcelProperty("接收者99duke*")
//    private String receiverUsername;
//
//    @ExcelProperty("发起者公会名称")
//    private String initiatorLeagueName;
//
//    @ExcelProperty("发起者公会头像")
//    private String initiatorLeagueAvatar;
//
//    @ExcelProperty("接收者公会名称")
//    private String receiverLeagueName;
//
//    @ExcelProperty("接收者公会头像")
//    private String receiverLeagueAvatar;
//
//    @ExcelProperty("消息内容")
//    private String content;
//
//    @ExcelProperty("钱")
//    private BigDecimal amount;
//
//    @ExcelProperty("创建时间")
//    private Date createTime;
//
//}
