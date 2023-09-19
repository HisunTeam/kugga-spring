package com.hisun.kugga.duke.system.api.s3;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.MD5;
import com.hisun.kugga.framework.common.util.date.DateTimeUtils;
import com.hisun.kugga.framework.file.core.client.FileClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@Service
public class S3FileUploadApiImpl implements S3FileUploadApi {

    private final static String USER_AVATAR_PATH = "/avatar/user";
    private final static String LEAGUE_AVATAR_PATH = "/avatar/league";
    private final static String LEAGUE_TEMP_AVATAR_PATH = "/avatar/temp/league-create";

    private final static String IMAGE_JPEG = "image/jpeg";

    public final static String SEPARATOR = "/";

    @Resource
    private FileClient avatarS3FileClient;

    @Override
    public String uploadUserAvatar(Long userId, byte[] content, String fileName, String contentType) {
        String s3ContentType = Optional.ofNullable(contentType).orElse(IMAGE_JPEG);
        String newFileName = getNewFileName(userId, content, fileName);
        return avatarS3FileClient.upload(content, USER_AVATAR_PATH + newFileName, s3ContentType);
    }

    @Override
    public String uploadLeagueAvatar(Long leagueId, byte[] content, String fileName, String contentType) {
        String s3ContentType = Optional.ofNullable(contentType).orElse(IMAGE_JPEG);
        String newFileName = getNewFileName(leagueId, content, fileName);
        return avatarS3FileClient.upload(content, LEAGUE_AVATAR_PATH + newFileName, s3ContentType);
    }

    @Override
    public String createUploadLeagueAvatar(byte[] content, String fileName, String contentType) {
        String s3ContentType = Optional.ofNullable(contentType).orElse(IMAGE_JPEG);
        String newFileName = getNewFileName(null, content, fileName);
        return avatarS3FileClient.upload(content, LEAGUE_TEMP_AVATAR_PATH + newFileName, s3ContentType);
    }

    @Override
    public String upload(byte[] content, String fileName, String contentType, String fileModule) {
        String s3ContentType = Optional.ofNullable(contentType).orElse(IMAGE_JPEG);
        String newFileName = getNewFileName(null, content, fileName);
        return avatarS3FileClient.upload(content, SEPARATOR + fileModule + newFileName, s3ContentType);
    }

    /**
     * 创建公会拷贝公会头像
     */
    @Override
    public String copyLeagueAvatar(Long id, String sourceFileUrl) {
        //获取文件路径
        String path = URLUtil.getPath(sourceFileUrl);
        String datePrefix = DateTimeUtils.getCurrentDateTimeStr();
        String randomName = UUID.fastUUID().toString();
        String fileType = FileUtil.getSuffix(path);
        String md5Prefix = MD5.create().digestHex16(id.toString());
        String targetPath = StrUtil.concat(true, LEAGUE_AVATAR_PATH, SEPARATOR, md5Prefix, SEPARATOR, datePrefix, SEPARATOR, randomName, ".", fileType);
        return avatarS3FileClient.copy(path, targetPath);
    }

    @NotNull
    private String getNewFileName(Long id, byte[] avatarFile, String fileName) {
        String randomName = UUID.fastUUID().toString();
        String datePrefix = DateTimeUtils.getCurrentDateStr();
        String fileType = FileTypeUtil.getType(new ByteArrayInputStream(avatarFile), fileName);
        if (id != null) {
            String md5Prefix = MD5.create().digestHex16(id.toString());
            return StrUtil.concat(true, SEPARATOR, md5Prefix, SEPARATOR, datePrefix, SEPARATOR, randomName, ".", fileType);
        } else {
            return StrUtil.concat(true, SEPARATOR, datePrefix, SEPARATOR, randomName, ".", fileType);
        }
    }

}
