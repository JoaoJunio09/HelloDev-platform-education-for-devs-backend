package br.com.joaojuniodev.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customApiConfig() {
        return new OpenAPI()
            .info(new Info()
                .title("REST API's RESTful by Blog Application with Java, Spring, Docker and Kubernetes")
                .version("1.0")
                .description("REST API's RESTful by Blog Application with Java, Spring, Docker and Kubernetes")
                .termsOfService("https://joaojunio-dev.vercel.app")
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://joaojunio-dev.vercel.app")));
    }
}
