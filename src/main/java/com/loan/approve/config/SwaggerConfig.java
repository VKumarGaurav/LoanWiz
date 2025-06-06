package com.loan.approve.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.loan.approve")) // Specify your controller package
                    .paths(PathSelectors.any())
                    .build();
        }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LoanWiz API")
                        .version("1.0")
                        .description("API documentation for LoanWiz Application")
                        .contact(new Contact().name("Support Team").email("gaurav@loanwiz.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

//http://localhost:8080/swagger-ui/
// http://localhost:8080/swagger-ui/index.html
// http://localhost:8080/v3/api-docs
//http://localhost:8080/swagger-ui.html