package com.example.springboot.datajpa.app.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    String header = request.getHeader("Authorization");

    if (!requiresAuthentication(header)) {
      chain.doFilter(request, response);
      return;
    }

    ClassPathResource resource = new ClassPathResource("server.jks");
    Key key = null;
    PublicKey publicKey = null;

    try {
      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(resource.getInputStream(), "jkspassword".toCharArray());
//      key = keystore.getKey("jwtkey", "jkspassword".toCharArray());
      Certificate cert = keystore.getCertificate("jwtkey");
      publicKey = cert.getPublicKey();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    boolean validToken;
    Claims token = null;

    try {
      token = Jwts.parser()
              .setSigningKey(publicKey)
              .parseClaimsJws(header.replace("Bearer ", ""))
              .getBody();
      validToken = true;
    } catch (JwtException | IllegalArgumentException e) {
      validToken = false;
      e.printStackTrace();
    }

    if (validToken) {

    }



  }

  protected boolean requiresAuthentication(String header) {
    if (header == null || !header.startsWith("Bearer ")) {
      return false;
    }
    return true;
  }


}
