package com.hisun.kugga.duke.user.cv.controller.app.recommendation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 推荐报告 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class RecommendationExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("推荐人编号")
    private Long recommenderId;

    @ExcelProperty("被推荐人编号")
    private Long recommendedId;

    @ExcelProperty("推荐信内容")
    private String content;

    @ExcelProperty("分享链接")
    private String shareLink;

    @ExcelProperty("创建时间")
    private Date createTime;

}
