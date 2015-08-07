package org.xueyu.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="org.xueyu")
public class AppConfig {

	@Bean
	UrlMapperPostBean urlMapperPostBean() {
		UrlMapperPostBean bean = new UrlMapperPostBean();
		return bean;
	}
}
