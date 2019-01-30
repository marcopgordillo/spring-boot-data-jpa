package com.example.springboot.datajpa.app.config;

import com.example.springboot.datajpa.app.auth.handler.LoginSuccessHandler;
import com.example.springboot.datajpa.app.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final LoginSuccessHandler loginSuccessHandler;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JpaUserDetailsService userDetailsService;

  public SpringSecurityConfig(LoginSuccessHandler loginSuccessHandler, BCryptPasswordEncoder passwordEncoder, JpaUserDetailsService userDetailsService) {
    this.loginSuccessHandler = loginSuccessHandler;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/webjars/**", "/listar**", "/locale").permitAll()
            /*.antMatchers("/ver/**", "/uploads/**").hasAnyRole("USER")
            .antMatchers("/form/**", "/eliminar/**", "/factura/**").hasAnyRole("ADMIN")*/
            .anyRequest().authenticated()
            .and()
              .formLogin()
                .successHandler(loginSuccessHandler)
                .loginPage("/login")
                .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/error_403");
  }

  @Autowired
  public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

    builder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);           ;
  }
}
