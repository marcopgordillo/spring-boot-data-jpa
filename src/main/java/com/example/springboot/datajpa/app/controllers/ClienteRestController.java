package com.example.springboot.datajpa.app.controllers;

import com.example.springboot.datajpa.app.models.service.IClienteService;
import com.example.springboot.datajpa.app.view.xml.ClienteList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final IClienteService clienteService;

  public ClienteRestController(IClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/listar")
  public ClienteList listar() {
    return new ClienteList(clienteService.findAll());
  }
}
