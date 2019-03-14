package org.gtdev.webapps.iaatraesamhsaat.configs;

import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.file.Paths;
import java.util.Locale;

@Configuration
public class AppConfig {
    @Bean
    public SpringPluginManager pluginManager() {
        return new SpringPluginManager(Paths.get("apps"));
    }
}
