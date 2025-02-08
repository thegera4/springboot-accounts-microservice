package com.jgmedellin.accounts;

import com.jgmedellin.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") // Enable JPA Auditing and set the AuditorAware bean name
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class}) // Enable Configuration Properties (Approach 3)
@OpenAPIDefinition( // OpenAPI 3.0 definition annotation to create the OpenAPI documentation with Swagger
				info = @Info(
								title = "Accounts microservice REST API",
								description = "EazyBank Accounts microservice REST API Documentation",
								version = "v1",
								contact = @Contact(
												name = "Gerardo Medellin",
												email = "thegera4@hotmail.com",
												url = "https://www.jgmedellin.com"
								),
								license = @License(
												name = "Apache 2.0",
												url = "http://www.apache.org/licenses/LICENSE-2.0"
								)
				),
				externalDocs = @ExternalDocumentation(
								description = "GitHub Repository",
								url = "https://github.com/thegera4/springboot-accounts-microservice"
				)

)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}