package com.hisun.kugga.framework.file.core.client.s3;

import cn.hutool.core.util.StrUtil;
import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.CopyObjectResultBean;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.bean.base.BaseObjectResponseBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.util.MetadataDirective;
import cn.ucloud.ufile.util.MimeTypeUtil;
import cn.ucloud.ufile.util.StorageType;
import com.hisun.kugga.framework.file.core.client.AbstractFileClient;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;

/**
 * 基于 S3 协议的文件客户端，实现 MinIO、阿里云、腾讯云、七牛云、华为云等云服务
 * <p>
 * S3 协议的客户端，采用亚马逊提供的 software.amazon.awssdk.s3 库
 *
 * @author 芋道源码
 */
@Log4j2
public class S3UcloudFileClient extends AbstractFileClient<S3UcloudFileClientConfig> {

    private static ObjectConfig objectConfig;
    private static ObjectAuthorization objectAuthorizer;

    public S3UcloudFileClient(Long id, S3UcloudFileClientConfig config) {
        super(id, config);
        objectAuthorizer = new UfileObjectLocalAuthorization(config.getPublicKey(), config.getPrivateKey());
        objectConfig = new ObjectConfig(config.getRegion(), config.getProxySuffix());
    }

    @Override
    protected void doInit() {
    }

    @Override
    public String upload(byte[] content, String path) {

        String filePath = startPathCheck(path);
        // 执行上传
        try {
            String mimeType = MimeTypeUtil.getMimeType(path);
            PutObjectResultBean response = UfileClient.object(objectAuthorizer, objectConfig)
                    .putObject(new ByteArrayInputStream(content), content.length, mimeType)
                    .nameAs(filePath)
                    .toBucket(config.getBucket())
                    // 配置文件存储类型，分别是标准、低频、冷存，对应有效值：STANDARD | IA | ARCHIVE
                    .withStorageType(StorageType.STANDARD)
                    // 配置进度监听
                    .setOnProgressListener((bytesWritten, contentLength1)
                            -> log.info(String.format("[progress] = %d%% - [%d/%d]", (int) (bytesWritten * 1.f / contentLength1 * 100), bytesWritten, contentLength1)))
                    .execute();
            log.info(String.format("[res] = %s", (response == null ? "null" : response.toString())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 拼接返回路径
        return config.getDomain() + path;
    }

    private String startPathCheck(String filePath) {
        // 由于ucloud path 文件如果是以/开头，需要拼接两个 // 才能访问
        // 能访问到 http://kuggaduke.hk.ufileos.com//avatar/user/a0b923820dcc509a/20220901063747/15e91043-f61e-4b11-a935-5ec06ac1b4ea.jpg
        // 不能访问到 http://kuggaduke.hk.ufileos.com/avatar/user/a0b923820dcc509a/20220901063747/15e91043-f61e-4b11-a935-5ec06ac1b4ea.jpg
        // 去除开头 为/
        if (StrUtil.startWith(filePath, "/")) {
            return StrUtil.sub(filePath, 1, filePath.length());
        }
        return filePath;
    }

    @Override
    public String upload(byte[] content, String path, String contentType) {
        return upload(content, path);
    }

    @Override
    public void delete(String path) {
        try {
            BaseObjectResponseBean response = UfileClient.object(objectAuthorizer, objectConfig)
                    .deleteObject(path, config.getBucket())
                    .execute();
            log.info(String.format("[res] = %s", (response == null ? "null" : response.toString())));
        } catch (UfileClientException | UfileServerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getContent(String path) throws Exception {
        return null;
    }

    @Override
    public String copy(String sourcePath, String targetPath) {
        String fileSourcePath = startPathCheck(sourcePath);
        String fileTargetPath = startPathCheck(targetPath);
        try {
            CopyObjectResultBean response = UfileClient.object(objectAuthorizer, objectConfig)
                    .copyObject(config.getBucket(), fileSourcePath)
                    .copyTo(config.getBucket(), fileTargetPath)
                    .withMetadataDirective(MetadataDirective.COPY)
                    .execute();
            log.info(String.format("[res] = %s", (response == null ? "null" : response.toString())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return config.getDomain() + targetPath;
    }

}
