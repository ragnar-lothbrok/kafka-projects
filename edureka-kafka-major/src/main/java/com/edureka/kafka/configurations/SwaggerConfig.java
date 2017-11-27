package com.edureka.kafka.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class is responsible for swagger configuration
 * 
 * @author raghunandan.gupta
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(new Predicate<RequestHandler>() {

			public boolean apply(RequestHandler input) {
				return input.getHandlerMethod().getMethod().getDeclaringClass().getPackage().getName()
						.startsWith("com.shadowfax.controllers");
			}
		}).paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Shadowfax POC API Documentation")
				.description("This will provide expected request and response of all APIS").version("VERSION1.0")
				.termsOfServiceUrl("").license("LICENSE").licenseUrl("").build();
	}
}
