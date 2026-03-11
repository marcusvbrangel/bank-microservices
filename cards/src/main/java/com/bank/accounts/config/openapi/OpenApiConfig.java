package com.bank.accounts.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "WolfBank - Cards",
        description = "Cards Microservices REST API Documentation",
        version = "v1",
        contact = @Contact(
                name = "Support",
                email = "support@wolfback.com",
                url = "support.wolfbank.com.br"
        ),
        license = @License(
                name = "Apache 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    )
)
public class OpenApiConfig {
}
