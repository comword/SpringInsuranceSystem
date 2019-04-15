package org.gtdev.webapps.iaatraesamhsaat;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class IaatraesamhsaatApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(IaatraesamhsaatApplication.class);
        appBuilder.properties("spring.config.name:application,dbconfig,mailconfig")
                .build()
                .run(args);
    }

}
