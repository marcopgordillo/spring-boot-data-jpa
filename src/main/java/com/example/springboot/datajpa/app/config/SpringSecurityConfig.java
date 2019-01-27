package com.example.springboot.datajpa.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
            .antMatchers("/ver/**", "/uploads/**").hasAnyRole("USER")
            .antMatchers("/form/**", "/eliminar/**", "/factura/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated();
  }

  @Autowired
  public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
    User.UserBuilder users = User.withDefaultPasswordEncoder();

    builder.inMemoryAuthentication()
            .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
            .withUser(users.username("user").password("12345").roles("USER"));
  }
}
