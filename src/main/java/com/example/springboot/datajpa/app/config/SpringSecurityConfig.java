package com.example.springboot.datajpa.app.config;

import com.example.springboot.datajpa.app.auth.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final LoginSuccessHandler loginSuccessHandler;

  public SpringSecurityConfig(LoginSuccessHandler loginSuccessHandler) {
    this.loginSuccessHandler = loginSuccessHandler;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/webjars/**", "/listar").permitAll()
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
    /*
     * Deprecated
     * UserBuilder users = User.withDefaultPasswordEncoder();
     * */

    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    User.UserBuilder users = User.builder().passwordEncoder(encoder::encode);

    builder.inMemoryAuthentication()
            .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
            .withUser(users.username("user").password("12345").roles("USER"));
  }
}
