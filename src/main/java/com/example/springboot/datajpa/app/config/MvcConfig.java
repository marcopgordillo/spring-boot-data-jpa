package com.example.springboot.datajpa.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.file.Paths;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${application.controller.uploads}")
  private String uploads;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    super.addResourceHandlers(registry);

    String resourcePath = Paths.get(uploads).toAbsolutePath().toUri().toString();

    log.info("ResourcePath: " + resourcePath);

    registry.addResourceHandler("/uploads/**")
    .addResourceLocations(resourcePath);
  }
}
