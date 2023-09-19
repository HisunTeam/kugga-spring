package com.hisun.kugga.duke.system.config;

import com.hisun.kugga.framework.file.core.client.s3.S3FileClientConfig;
import com.hisun.kugga.framework.file.core.client.s3.S3UcloudFileClientConfig;
import com.hisun.kugga.framework.file.core.enums.FileStorageEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.hisun.kugga.framework.file.core.enums.FileStorageEnum.S3;

@ConfigurationProperties(prefix = "kugga.s3")
@Data
public class S3AvatarClientConfig {

    /**
     * 配置类型
     */
    private FileStorageEnum type = S3;

    private S3FileClientConfig fileConfig;

    private S3UcloudFileClientConfig ucloudFileConfig;
}
