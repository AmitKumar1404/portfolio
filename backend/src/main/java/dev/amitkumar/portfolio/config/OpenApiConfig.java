package dev.amitkumar.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI portfolioOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Amit Kumar — Portfolio API")
                        .version("v1")
                        .description("Public API for the Amit Kumar portfolio.")
                        .contact(new Contact().name("Amit Kumar")));
    }
}
