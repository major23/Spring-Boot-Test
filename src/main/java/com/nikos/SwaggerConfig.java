package com.nikos;

import static springfox.documentation.builders.PathSelectors.regex;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("default")
public class SwaggerConfig {

	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.directModelSubstitute(Iterable.class, Collection.class)
				.directModelSubstitute(LocalDate.class, String.class)
				.directModelSubstitute(LocalDateTime.class, String.class)
				.directModelSubstitute(ZonedDateTime.class, String.class)
				.apiInfo(apiInfo())
				.select()
				.paths(regex(".*(" +
						Config.USERS_CONTROLLER_PREFIX + "|" +
						Config.TOPICS_CONTROLLER_PREFIX + "|" +
						Config.JOBS_CONTROLLER_PREFIX + ").*"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Nikos Test Swagger")
				.description("Swagger decription")
				.termsOfServiceUrl("Terms Of Service")
				.contact(new Contact(Application.getOwnerName() + " " + Application.getOwnerLastName(), "http://www.nikos.com", "nikos@gmail.com"))
				.license("Licence Title")
				.licenseUrl("https://www.google.com")
				.version("1.0")
				.build();
	}

}
