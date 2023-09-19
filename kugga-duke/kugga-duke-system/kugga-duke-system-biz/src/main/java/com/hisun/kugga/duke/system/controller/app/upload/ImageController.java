package com.hisun.kugga.duke.system.controller.app.upload;

import com.hisun.kugga.duke.system.service.ImageService;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.hisun.kugga.framework.common.pojo.CommonResult.success;

/**
 * @author: zhou_xiong
 */
@Api(tags = "公共的图片上传")
@RestController
@RequestMapping("/system/image")
@Validated
public class ImageController {
    @Resource
    private ImageService imageService;

    @PostMapping(value = "/upload")
    @ApiOperation(value = "图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "单文件上传", required = true, dataType = "MultipartFile", allowMultiple = true, paramType = "form"),
            @ApiImplicitParam(name = "fileModule", value = "文件模块(posts[帖子模块]。。。)", paramType = "query", dataType = "string", defaultValue = "test")
    })
    public CommonResult<String> fileUpload(@RequestPart("file") MultipartFile file, @RequestParam("fileModule") String fileModule) {
        return success(imageService.fileUpload(file, fileModule));
    }
}
