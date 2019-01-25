package com.example.springboot.datajpa.app.controllers;

import com.example.springboot.datajpa.app.models.dao.IClienteDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClienteController {

  @Qualifier("clienteDaoJPA")
  private final IClienteDao clienteDao;

  @Value("${application.controller.titulo}")
  private String titulo;

  public ClienteController(IClienteDao clienteDao) {
    this.clienteDao = clienteDao;
  }

  @RequestMapping(value = "/listar", method = RequestMethod.GET)
  public String listar(Model model) {
    model.addAttribute("titulo", titulo);
    model.addAttribute("clientes", clienteDao.findAll());
    return "listar";
  }
}
