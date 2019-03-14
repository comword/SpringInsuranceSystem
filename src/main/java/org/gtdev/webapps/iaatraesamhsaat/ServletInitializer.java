package org.gtdev.webapps.iaatraesamhsaat;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IaatraesamhsaatApplication.class)
				.properties("spring.config.name:application,dbconfig");
	}

}

