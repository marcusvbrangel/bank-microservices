package com.bank.accounts.config.openapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservices RESP API Documentation",
                description = "WolfBank Accounts microservices REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Support",
                        email = "support@wolfbank.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "WolfBank accounts microservices REST API Documentation",
                url = "docs@wolfbank.com"
        )
)
class OpenApiConfig {

}
