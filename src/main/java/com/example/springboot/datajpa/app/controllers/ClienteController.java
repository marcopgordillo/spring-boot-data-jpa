package com.example.springboot.datajpa.app.controllers;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import com.example.springboot.datajpa.app.models.service.IClienteService;
import com.example.springboot.datajpa.app.models.service.IUploadFileService;
import com.example.springboot.datajpa.app.util.paginator.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final IClienteService clienteService;
  private final IUploadFileService uploadFileService;

  @Value("${application.controller.titulo}")
  private String titulo;

  @Value("${application.controller.uploads}")
  private String uploads;

  public ClienteController(IClienteService clienteService, IUploadFileService uploadFileService) {
    this.clienteService = clienteService;
    this.uploadFileService = uploadFileService;
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

  @GetMapping("/uploads/{filename:.*}")
  public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

    Resource recurso = null;
    try {
      recurso = uploadFileService.load(filename);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (recurso != null ? recurso.getFilename() : null) + "\"")
            .body(recurso);
  }

  @GetMapping(value = "/ver/{id}")
  public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

    Cliente cliente = clienteService.findOne(id);

    if (cliente == null) {
      flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
      return "redirect:/listar";
    }

    model.put("titulo", "Detalla cliente: " + cliente.getNombreCompleto());
    model.put("cliente", cliente);

    return "ver";
  }
  
  @RequestMapping(value="/form", method = RequestMethod.POST)
  public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

  	if(result.hasErrors()) {
  		model.addAttribute("titulo", "Crear Cliente");
  		return "form";
  	}

  	// En el caso de cambiar la foto
  	if (!foto.isEmpty()) {

  	  if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
        uploadFileService.delete(cliente.getFoto());
      }

      String uniqueFileName = null;
      try {
        uniqueFileName = uploadFileService.copy(foto);
      } catch (IOException e) {
        e.printStackTrace();
      }

      cliente.setFoto(uniqueFileName);
      flash.addFlashAttribute("info", "Has subido corectamente '" + uniqueFileName + "'");

    }

  	if (cliente.getFoto() == null) {
  	  cliente.setFoto("");
    }

  	String mensajeFlash = (cliente.getId() != null) ? "¡Cliente actualizado con éxito!" : "¡Cliente creado con éxito!";

  	clienteService.save(cliente);
  	status.setComplete();
  	flash.addFlashAttribute("success", mensajeFlash);
  	return "redirect:/listar";
  }
  
  @RequestMapping(value="/form/{id}")
  public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
  	
  	Cliente cliente;
  	
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
      Cliente cliente = clienteService.findOne(id);
  		clienteService.delete(id);
      flash.addFlashAttribute("success", "¡Cliente eliminado con éxito!");

      if (uploadFileService.delete(cliente.getFoto())){
        flash.addFlashAttribute("info", "¡Foto " + cliente.getFoto() + " eliminada con exito!");
      }
  	}

  	return "redirect:/listar";
  }
}
