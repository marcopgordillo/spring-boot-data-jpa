package com.example.springboot.datajpa.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
    User.UserBuilder users = User.withDefaultPasswordEncoder();

    builder.inMemoryAuthentication()
            .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
            .withUser(users.username("user").password("12345").roles("USER"));
  }
}
