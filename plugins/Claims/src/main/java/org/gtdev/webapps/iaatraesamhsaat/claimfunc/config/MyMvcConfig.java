package org.gtdev.webapps.iaatraesamhsaat.claimfunc.config;

import com.example.demo.component.MyLocaleReslover;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleReslover();
    }
}


