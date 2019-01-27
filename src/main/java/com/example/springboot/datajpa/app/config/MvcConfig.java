package com.example.springboot.datajpa.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${application.controller.uploads}")
  private String uploads;

  /*@Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    String resourcePath = Paths.get(uploads).toAbsolutePath().toUri().toString();

    log.info("ResourcePath: " + resourcePath);

    registry.addResourceHandler("/uploads/**")
    .addResourceLocations(resourcePath);
  }*/
}
