package com.hisun.kugga.duke.system.controller.app.upload.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 上传图片信息
 *
 * @author zhou_xiong
 */
@ApiModel("上传图片信息 VO")
@Data
public class UploadImageRspVO {
    @ApiModelProperty(value = "图片id")
    private Long imageId;

    @ApiModelProperty(value = "原始图链接")
    private String oriUrl;

    @ApiModelProperty(value = "缩略图链接")
    private String compressUrl;

    @ApiModelProperty(value = "小图链接")
    private String smallUrl;


}
