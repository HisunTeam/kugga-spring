package com.hisun.kugga.duke.system.api.s3;

public interface S3FileUploadApi {

    /**
     * 上传用户头像
     */
    String uploadUserAvatar(Long userId, byte[] content, String fileName, String contentType);

    /**
     * 上传工会头像
     */
    String uploadLeagueAvatar(Long leagueId, byte[] content, String fileName, String contentType);

    /**
     * 创建公会临时保存图片
     */
    String createUploadLeagueAvatar(byte[] content, String fileName, String contentType);

    /**
     * 拷贝公会头像
     */
    String copyLeagueAvatar(Long id, String sourceFileUrl);

    /**
     * 上传图片
     *
     * @param content
     * @param fileName
     * @param contentType
     * @param fileModule
     * @return
     */
    String upload(byte[] content, String fileName, String contentType, String fileModule);
}
