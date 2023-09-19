package com.hisun.kugga.framework.file.core.client.s3;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.hisun.kugga.framework.file.core.client.AbstractFileClient;
import io.minio.*;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;

import static com.hisun.kugga.framework.file.core.client.s3.S3FileClientConfig.ENDPOINT_ALIYUN;

/**
 * 基于 S3 协议的文件客户端，实现 MinIO、阿里云、腾讯云、七牛云、华为云等云服务
 * <p>
 * S3 协议的客户端，采用亚马逊提供的 software.amazon.awssdk.s3 库
 *
 * @author 芋道源码
 */
@Log4j2
public class S3FileClient extends AbstractFileClient<S3FileClientConfig> {

    private MinioClient client;

    public S3FileClient(Long id, S3FileClientConfig config) {
        super(id, config);
    }

    @Override
    protected void doInit() {
        // 补全 domain
        if (StrUtil.isEmpty(config.getDomain())) {
            config.setDomain(buildDomain());
        }
        // 初始化客户端
        client = MinioClient.builder()
                .endpoint(buildEndpointURL()) // Endpoint URL
                .region(buildRegion()) // Region
                .credentials(config.getAccessKey(), config.getAccessSecret()) // 认证密钥
                .build();
    }

    /**
     * 基于 endpoint 构建调用云服务的 URL 地址
     *
     * @return URI 地址
     */
    private String buildEndpointURL() {
        // 如果已经是 http 或者 https，则不进行拼接.主要适配 MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return config.getEndpoint();
        }
        return StrUtil.format("https://{}", config.getEndpoint());
    }

    /**
     * 基于 bucket + endpoint 构建访问的 Domain 地址
     *
     * @return Domain 地址
     */
    private String buildDomain() {
        // 如果已经是 http 或者 https，则不进行拼接.主要适配 MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return StrUtil.format("{}/{}", config.getEndpoint(), config.getBucket());
        }
        // 阿里云、腾讯云、华为云都适合。七牛云比较特殊，必须有自定义域名
        return StrUtil.format("https://{}.{}", config.getBucket(), config.getEndpoint());
    }

    /**
     * 基于 bucket 构建 region 地区
     *
     * @return region 地区
     */
    private String buildRegion() {
        // 阿里云必须有 region，否则会报错
        if (config.getEndpoint().contains(ENDPOINT_ALIYUN)) {
            return StrUtil.subBefore(config.getEndpoint(), '.', false)
                    .replaceAll("-internal", ""); // 去除内网 Endpoint 的后缀
        }
        return null;
    }

    @Override
    public String upload(byte[] content, String path) {
        // 执行上传
        try {
            client.putObject(PutObjectArgs.builder()
                    .bucket(config.getBucket()) // bucket 必须传递
                    .object(path) // 相对路径作为 key
                    .stream(new ByteArrayInputStream(content), content.length, -1) // 文件内容
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 拼接返回路径
        return config.getDomain() + path;
    }

    @Override
    public String upload(byte[] content, String path, String contentType) {
        // 执行上传
        try {
            client.putObject(PutObjectArgs.builder()
                    .bucket(config.getBucket()) // bucket 必须传递
                    .object(path) // 相对路径作为 key
                    .stream(new ByteArrayInputStream(content), content.length, -1) // 文件内容
                    .contentType(contentType)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 拼接返回路径
        return config.getDomain() + path;
    }

    @Override
    public void delete(String path) throws Exception {
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(config.getBucket()) // bucket 必须传递
                .object(path) // 相对路径作为 key
                .build());
    }

    @Override
    public byte[] getContent(String path) throws Exception {
        GetObjectResponse response = client.getObject(GetObjectArgs.builder()
                .bucket(config.getBucket()) // bucket 必须传递
                .object(path) // 相对路径作为 key
                .build());
        return IoUtil.readBytes(response);
    }

    @Override
    public String copy(String sourcePath, String targetPath) {
        // 执行上传
        try {
            ObjectWriteResponse objectWriteResponse = client.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(config.getBucket())
                            .object(targetPath)
                            .source(
                                    CopySource.builder()
                                            .bucket(config.getBucket())
                                            .object(startBucketPathCheck(sourcePath))
                                            .build())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return config.getDomain() + targetPath;
    }


    private String startBucketPathCheck(String path) {
        // 去除开头 为/public 或者public
        if (StrUtil.startWith(path, "/" + config.getBucket())) {
            return StrUtil.sub(path, 1 + config.getBucket().length(), path.length());
        }

        if (StrUtil.startWith(path, config.getBucket())) {
            return StrUtil.sub(path, config.getBucket().length(), path.length());
        }

        return path;
    }

}
