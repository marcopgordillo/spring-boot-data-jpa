package com.example.springboot.datajpa.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  /*@Value("${application.controller.uploads}")
  private String uploads;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    String resourcePath = Paths.get(uploads).toAbsolutePath().toUri().toString();

    log.info("ResourcePath: " + resourcePath);

    registry.addResourceHandler("/uploads/**")
    .addResourceLocations(resourcePath);
  }*/

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/error_403").setViewName("errors/error_403");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
