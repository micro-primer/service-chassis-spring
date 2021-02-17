package com.pttrn42.microprimer.servicechassispring.infrastructure.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

import static java.util.Collections.emptyList;

@Configuration
@EnableSwagger2
@Import(SwaggerUiWebMvcConfigurer.class)
class SwaggerConfiguration {

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(Predicate.not(PathSelectors.regex("/actuator/.*")))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfo(
                        "service-chassis-springboot",
                        "This service provides an API for ...",
                        "",
                        "",
                        new Contact("", "", ""),
                        "",
                        "",
                        emptyList()
                ));
    }
}
