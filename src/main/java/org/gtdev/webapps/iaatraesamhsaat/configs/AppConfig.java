package org.gtdev.webapps.iaatraesamhsaat.configs;

import lombok.Getter;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Paths;

@Configuration
public class AppConfig {
    @Bean
    public SpringPluginManager pluginManager() {
        return new SpringPluginManager(Paths.get("apps"));
    }

    @Getter
    @Configuration
    public static class DataPathConfig {

        @Value("${config.paths.upload:data/upload}")
        private String uploadPath;

        @Value("${config.paths.attachments:data/attachments}")
        private String attachmentPath;
    }

}
