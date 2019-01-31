package com.example.springboot.datajpa.app.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JWTService {

  String create(Authentication authentication);

  boolean validate(String token);

  Claims getClaims(String token);

  String getUsername(String token);

  Collection<? extends GrantedAuthority> getRoles(String token);

  String resolve(String token);
}
