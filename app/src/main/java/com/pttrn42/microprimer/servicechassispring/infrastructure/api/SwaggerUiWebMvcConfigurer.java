package com.pttrn42.microprimer.servicechassispring.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {
  private final static String BASE_URL = "/actuator";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.
        addResourceHandler(BASE_URL+ "/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
        .resourceChain(false);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController(BASE_URL + "/swagger-ui/")
        .setViewName("forward:" + BASE_URL + "/swagger-ui/index.html");
  }
}