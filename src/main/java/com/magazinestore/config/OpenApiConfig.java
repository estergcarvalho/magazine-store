package com.magazinestore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Magazine Store")
                .description("Projeto de e-commerce com o objetivo de aprimorar minhas habilidades de desenvolvimento")
                .version("1.0"));
    }

}