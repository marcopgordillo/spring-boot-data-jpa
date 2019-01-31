package com.example.springboot.datajpa.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthoriesMixin {

  @JsonCreator
  public SimpleGrantedAuthoriesMixin(@JsonProperty("authority") String role) {
  }
}
