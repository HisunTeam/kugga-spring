package com.hisun.kugga.duke.system.config;

import com.hisun.kugga.framework.file.core.client.FileClient;
import com.hisun.kugga.framework.file.core.client.s3.S3FileClient;
import com.hisun.kugga.framework.file.core.client.s3.S3UcloudFileClient;
import com.hisun.kugga.framework.file.core.enums.FileStorageEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 文件配置类
 *
 * @author toi
 */
@Configuration
@Import(S3AvatarClientConfig.class)
public class KuggaS3AutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "kugga.s3", value = "enable")
    public FileClient avatarS3FileClient(S3AvatarClientConfig clientConfig) {
        FileClient fileClient = null;
        if (clientConfig.getType() == FileStorageEnum.S3) {
            S3FileClient s3FileClient = new S3FileClient(Long.MIN_VALUE, clientConfig.getFileConfig());
            s3FileClient.init();
            fileClient = s3FileClient;
        } else if (clientConfig.getType() == FileStorageEnum.S3_UCLOUD) {
            S3UcloudFileClient s3UcloudFileClient = new S3UcloudFileClient(Long.MIN_VALUE + 1, clientConfig.getUcloudFileConfig());
            s3UcloudFileClient.init();
            fileClient = s3UcloudFileClient;
        }
        return fileClient;
    }

}
