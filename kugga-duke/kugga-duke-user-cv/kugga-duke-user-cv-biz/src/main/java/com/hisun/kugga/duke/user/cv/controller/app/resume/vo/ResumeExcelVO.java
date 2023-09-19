package com.hisun.kugga.duke.user.cv.controller.app.resume.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 个人简历信息 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class ResumeExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("用户编号")
    private Long resumeUserId;

    @ExcelProperty("个人简历内容")
    private String content;

    @ExcelProperty("创建时间")
    private Date createTime;

}
