package com.example.springboot.datajpa.app.auth.service;

import com.example.springboot.datajpa.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService {

  public static final String SECRET = Base64Utils.encodeToString("Alguna.Clave.Secreta.123456".getBytes());

  public static final String STORAGE_SECRET = "jkspassword";

  public static final String KEY_NAME = "jwtkey";

  public static final String KEY_SECRET = "keypassword";

  public static final long EXPIRATION_DATE = 14400000L;

  public static final String TOKEN_PREFIX = "Bearer ";

  public static final String HEADER_STRING = "Authorization";

  public static final String CONTENT_TYPE = "application/json";

  private static final String  KEYS_FILE = "certs/server.jks";

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${application.controller.certs}")
  private String certs;

  @Override
  public String create(Authentication auth) throws IOException {

    String username = ((User) auth.getPrincipal()).getUsername();

    Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

    Claims claims = Jwts.claims();
    claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
//                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
            .signWith(getPrivateKey())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
            .compact();
  }

  @Override
  public boolean validate(String token) {

    try {
      getClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Claims getClaims(String token) {
    return Jwts.parser()
            .setSigningKey(getPublicKey())
            .parseClaimsJws(resolve(token))
            .getBody();
  }

  @Override
  public String getUsername(String token) {
    return getClaims(token).getSubject();
  }

  @Override
  public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
    Object roles = getClaims(token).get("authorities");

    return Arrays.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class).readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
  }

  @Override
  public String resolve(String token) {
    return token != null && token.startsWith(TOKEN_PREFIX) ? token.replace(TOKEN_PREFIX, "") : null;
  }

  private PublicKey getPublicKey() {

    PublicKey publicKey = null;

    try {
      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(getResource().getInputStream(), STORAGE_SECRET.toCharArray());
      Certificate cert = keystore.getCertificate(KEY_NAME);

      publicKey = cert.getPublicKey();

    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
    }

    return publicKey;
  }

  private Key getPrivateKey() {

    Key key = null;

    try {
      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(getResource().getInputStream(), STORAGE_SECRET.toCharArray());

      key = keystore.getKey(KEY_NAME, STORAGE_SECRET.toCharArray());

    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | IOException e) {
      e.printStackTrace();
    }

    return key;
  }

  private ClassPathResource getResource() {

    return new ClassPathResource(KEYS_FILE);
  }
}
