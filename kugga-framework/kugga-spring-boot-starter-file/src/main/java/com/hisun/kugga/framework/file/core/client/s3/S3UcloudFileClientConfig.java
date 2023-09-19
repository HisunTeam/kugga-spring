package com.hisun.kugga.framework.file.core.client.s3;

import com.hisun.kugga.framework.file.core.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * ucloud 文件客户端的配置类
 *
 * @author toi
 * <p>
 * 上传文件：
 * https://docs.ucloud.cn/api/ufile-api/put_file
 * <p>
 * Api签名：
 * https://docs.ucloud.cn/ufile/api/authorization
 * <p>
 * java SDK:
 * https://github.com/ucloud/ufile-sdk-java
 */
@Data
public class S3UcloudFileClientConfig implements FileClientConfig {

    /**
     * 用户公钥
     */
    protected String publicKey;
    /**
     * 用户私钥
     */
    protected String privateKey;
    /**
     * 仓库地区 (eg: 'cn-bj')
     */
    private String region = "hk";
    /**
     * 代理后缀 (eg: 'ufileos.com')
     */
    private String proxySuffix = "ufileos.com";
    /**
     * 自定义域名
     * 6. ucloud:
     */
    @URL(message = "domain 必须是 URL 格式")
    private String domain = "https://kuggaduke.hk.ufileos.com";
    /**
     * 存储 Bucket
     */
    @NotNull(message = "bucket 不能为空")
    private String bucket = "kuggaduke";

}
