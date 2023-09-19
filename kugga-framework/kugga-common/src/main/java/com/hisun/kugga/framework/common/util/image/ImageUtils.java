package com.hisun.kugga.framework.common.util.image;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 图片相关
 */
public class ImageUtils {

    /**
     * 平台当前支持图片格式
     */
    public static boolean support(String fileName) {
        String suffix = FileNameUtil.getSuffix(fileName);
        return StrUtil.equalsAnyIgnoreCase(suffix, "pbm", "jpg", "jpeg", "jpe", "png", "ico", "gif");
    }

    public static boolean sizeLimit(long fileSize) {
        return fileSize > 1024 * 1024;
    }

    /**
     * 限制10M
     *
     * @param fileSize
     * @return
     */
    public static boolean generalSizeLimit(long fileSize) {
        return fileSize > 1024 * 1024 * 10;
    }

}
