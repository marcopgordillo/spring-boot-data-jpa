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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Collection;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

  private final Logger logger = LoggerFactory.getLogger(getClass());

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

  @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
  public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication) {

    if (authentication != null) {
      logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null) {
      logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado, username: ".concat(auth.getName()));
    }

    if (hasRole("ROLE_ADMIN")) {
      logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
    } else {
      logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
    }

    Pageable pageRequest = PageRequest.of(page, 5);

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

    if (!clienteService.exists(id)) {
      flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
      return "redirect:/listar";
    }

    Cliente cliente = clienteService.fetchByIdWithFacturas(id);

    model.put("titulo", "Detalle cliente: " + cliente.getNombreCompleto());
    model.put("cliente", cliente);

    return "ver";
  }
  
  @RequestMapping(value="/form", method = RequestMethod.POST)
  public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("foto-upload") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

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
  	
  	if (id > 0) {

  		if (!clienteService.exists(id)) {
        flash.addFlashAttribute("error", "¡El ID del cliente no existe en la Base de datos!");
        return "redirect:/listar";
      }
  	} else {
      flash.addFlashAttribute("error", "¡El ID del cliente no puede ser cero!");
  		return "redirect:/listar";
  	}

    Cliente cliente = clienteService.findOne(id);
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

  private boolean hasRole(String role) {

    SecurityContext context = SecurityContextHolder.getContext();

    if (context == null) {
      return false;
    }

    Authentication auth = context.getAuthentication();

    if (auth == null) {
      return false;
    }

    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    /*for (GrantedAuthority authority : authorities) {
      if (role.equals(authority.getAuthority())) {
        logger.info("Hola usuario '".concat(auth.getName()).concat("' tu rol es: ").concat(authority.getAuthority()));
        return true;
      }
    }

    return false;*/

    return authorities.contains(new SimpleGrantedAuthority(role));
  }
}
