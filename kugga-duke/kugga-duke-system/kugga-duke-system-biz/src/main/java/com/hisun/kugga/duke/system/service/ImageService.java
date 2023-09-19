package com.hisun.kugga.duke.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhou_xiong
 */
public interface ImageService {
    /**
     * 图片上传
     *
     * @param file
     * @param fileModule
     * @return
     */
    String fileUpload(MultipartFile file, String fileModule);
}
