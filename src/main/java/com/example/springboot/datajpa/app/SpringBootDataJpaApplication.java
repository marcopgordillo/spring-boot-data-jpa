package com.example.springboot.datajpa.app;

import com.example.springboot.datajpa.app.models.service.IUploadFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

  private final IUploadFileService uploadFileService;
  private final BCryptPasswordEncoder passwordEncoder;

  public SpringBootDataJpaApplication(IUploadFileService uploadFileService, BCryptPasswordEncoder passwordEncoder) {
    this.uploadFileService = uploadFileService;
    this.passwordEncoder = passwordEncoder;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataJpaApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    uploadFileService.deleteAll();
    uploadFileService.init();

    String password = "12345";
    for (int i = 0; i<2; i++) {
      String bcryptPassword = passwordEncoder.encode(password);
      System.out.println(bcryptPassword);
    }
  }
}

