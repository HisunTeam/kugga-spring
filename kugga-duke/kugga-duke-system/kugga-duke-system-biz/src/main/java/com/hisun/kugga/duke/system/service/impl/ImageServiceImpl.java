package com.hisun.kugga.duke.system.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.system.api.s3.S3FileUploadApi;
import com.hisun.kugga.duke.system.service.ImageService;
import com.hisun.kugga.framework.common.util.image.ImageUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.duke.system.api.s3.S3FileUploadApiImpl.SEPARATOR;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author: zhou_xiong
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Resource
    private S3FileUploadApi s3FileUploadApi;

    @SneakyThrows
    @Override
    public String fileUpload(MultipartFile file, String fileModule) {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        if (ImageUtils.generalSizeLimit(file.getSize())) {
            throw exception(FILE_IMAGE_SIZE_LIMIT);
        }
        if (!ImageUtils.support(file.getOriginalFilename())) {
            throw exception(FILE_IMAGE_NOT_SUPPORT);
        }
        byte[] content = IoUtil.readBytes(file.getInputStream());
        if (StrUtil.contains(fileModule, SEPARATOR)) {
            throw exception(FILE_MODULE_ILLEGAL);
        }
        return s3FileUploadApi.upload(content, file.getOriginalFilename(), file.getContentType(), fileModule);
    }
}
