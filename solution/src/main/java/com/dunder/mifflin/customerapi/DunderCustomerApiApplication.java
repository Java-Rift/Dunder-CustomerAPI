package com.dunder.mifflin.customerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Spring boot, REST CRUD operation for Dunder Mifflin Customer API.
 *
 * The human-readable HTML doc/testing end-point:
 * 			http://localhost:9092/swagger-ui/
 *
 * @author Edom Mesfin
 * @since 2022
 */
@SpringBootApplication
public class DunderCustomerApiApplication {

	/* ========== Take-Home exercise ===========
	 * Please create an application in no more than 2 hours.
	 *
	 * Keep the following concerns in mind and be able to explain:
	 * - your code structure
	 * - the problems you solve in your code
	 * - your ability to simplify complexity
	 * - your ability to ensure quality - testing & documentation
	 * - the ease of understanding your code
	 *
	 * Create a Customer API that would help to power an online paper store (Dunder Mifflin Paper Company).
	 * Your API should be able to Create, View, List, and Delete customers.
	 *
	 * Please create in a GitHub (or bitbucket etc) repository with your code and instructions on how to run your application.
	 * Once completed answer the following question in the git repo (no code needed):
	 * - If you were to scale your customer API service to millions of customers how would you build it differently?
	 */
	public static void main(String[] args) {
		SpringApplication.run(DunderCustomerApiApplication.class, args);
	}


	@Bean
	public Docket SwaggerDocConfig() { //part of springfox.documentation.spring.web.plugins, Return a prepared Docket instance...
		return new Docket(DocumentationType.SWAGGER_2) // Api builder
				.select()    // add selector builder to configuration...
				.paths(PathSelectors.ant("/dunder/customers/*"))   // Only include customer end-points and not other system end-points like error handlers...
				.apis(RequestHandlerSelectors.basePackage("com.dunder.mifflin.customerapi"))  // Only include specific packages....
				.build()
				.apiInfo(apiDetails()); // Add Detail info related to this API
	}

	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Dunder Mifflin Customer Rest API",
				"Rest CRUD services for Dunder Customer management API.",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Edom Mesfin", "https://www.linkedin.com/in/codecoffee/", "edom.mesfin.cs@gmail.com"),
				"API License",
				"https://www.linkedin.com/in/codecoffee/",
				Collections.emptyList());
	}

}
