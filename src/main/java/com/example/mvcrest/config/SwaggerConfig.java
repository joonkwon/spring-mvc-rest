package com.example.mvcrest.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		Contact contact = new Contact(
				"This is Contact", 
				"http://this.is.contact", 
				"contact@this.com");
		
		return new ApiInfo(
				"This is Title", 
				"This is description", 
				"1.0.2", 
				"http://termsOfServiceUrl", 
				contact, 
				"This is license",
				"http://licenseUrl.com/license",
				new ArrayList<>());
	}
}
