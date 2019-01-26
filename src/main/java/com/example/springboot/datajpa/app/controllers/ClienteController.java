package com.example.springboot.datajpa.app.controllers;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import com.example.springboot.datajpa.app.models.service.IClienteService;
import com.example.springboot.datajpa.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
  public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
    Pageable pageRequest = new PageRequest(page, 5);

    Page<Cliente> clientes = clienteService.findAll(pageRequest);

    PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

    model.addAttribute("titulo", titulo);
    model.addAttribute("clientes", clientes);
    model.addAttribute("page", pageRender);
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
  public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

  	if(result.hasErrors()) {
  		model.addAttribute("titulo", "Crear Cliente");
  		return "form";
  	}

  	if (!foto.isEmpty()) {

      Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
      String rootPath = directorioRecursos.toFile().getAbsolutePath();
      try {
        byte[] bytes = foto.getBytes();
        Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
        Files.write(rutaCompleta, bytes);
        cliente.setFoto(foto.getOriginalFilename());
        flash.addFlashAttribute("info", "Has subido corectamente '" + foto.getOriginalFilename() + "'");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  	String mensajeFlash = (cliente.getId() != null) ? "¡Cliente actualizado con éxito!" : "¡Cliente creado con éxito!";

  	clienteService.save(cliente);
  	status.setComplete();
  	flash.addFlashAttribute("success", mensajeFlash);
  	return "redirect:/listar";
  }
  
  @RequestMapping(value="/form/{id}")
  public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
  	
  	Cliente cliente = null;
  	
  	if (id > 0) {
  		cliente = clienteService.findOne(id);
  		if (cliente == null) {
        flash.addFlashAttribute("error", "¡El ID del cliente no existe en la Base de datos!");
        return "redirect:/listar";
      }
  	} else {
      flash.addFlashAttribute("error", "¡El ID del cliente no puede ser cero!");
  		return "redirect:/listar";
  	}
  	model.put("cliente", cliente);
  	model.put("titulo", "Editar Cliente");
  	return "form";
  }
  
  @RequestMapping(value="/eliminar/{id}")
  public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
  	if(id > 0) {
  		clienteService.delete(id);
  	}
    flash.addFlashAttribute("success", "¡Cliente eliminado con éxito!");
  	return "redirect:/listar";
  }
}
