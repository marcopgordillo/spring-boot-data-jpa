package com.example.springboot.datajpa.app;

import com.example.springboot.datajpa.app.models.service.IUploadFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

  private final IUploadFileService uploadFileService;

  public SpringBootDataJpaApplication(IUploadFileService uploadFileService) {
    this.uploadFileService = uploadFileService;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataJpaApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    uploadFileService.deleteAll();
    uploadFileService.init();
  }
}

