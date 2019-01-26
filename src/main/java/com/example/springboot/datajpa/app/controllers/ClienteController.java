package com.example.springboot.datajpa.app.controllers;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import com.example.springboot.datajpa.app.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

  private final IClienteService clienteService;

  @Value("${application.controller.titulo}")
  private String titulo;

  public ClienteController(IClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @RequestMapping(value = "/listar", method = RequestMethod.GET)
  public String listar(Model model) {
    model.addAttribute("titulo", titulo);
    model.addAttribute("clientes", clienteService.findAll());
    return "listar";
  }
  
  @RequestMapping(value="/form")
  public String crear(Map<String, Object> model) {
  	Cliente cliente = new Cliente();
  	
  	model.put("cliente", cliente);
  	model.put("titulo", "Crear Cliente");
  	return "form";
  }
  
  @RequestMapping(value="/form", method = RequestMethod.POST)
  public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) {
  	if(result.hasErrors()) {
  		model.addAttribute("titulo", "Crear Cliente");
  		return "form";
  	}
  	clienteService.save(cliente);
  	status.setComplete();
  	return "redirect:/listar";
  }
  
  @RequestMapping(value="/form/{id}")
  public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
  	
  	Cliente cliente = null;
  	
  	if (id > 0) {
  		cliente = clienteService.findOne(id);
  	} else {
  		return "redirect:/listar";
  	}
  	model.put("cliente", cliente);
  	model.put("titulo", "Editar Cliente");
  	return "form";
  }
  
  @RequestMapping(value="/eliminar/{id}")
  public String eliminar(@PathVariable(value="id") Long id) {
  	if(id > 0) {
  		clienteService.delete(id);
  	}  	
  	return "redirect:/listar";
  }
}
