package com.roaa.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.roaa.accounts.controller") })
@EnableJpaRepositories("com.roaa.accounts.repository")
@EntityScan("com.roaa.accounts.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info =@Info(
				title = "Accounts Microservice Rest API Documentation",
				description = "EazyBank Accounts Microservice Rest API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Ahmed Mohamed",
						email = "ahmedmohmmed1992@gmail",
						url = "https://github.com/ahmedabdellatifss"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank Documentation",
				url = "https://github.com/ahmedabdellatifss"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
