package com.example.springboot.datajpa.app.controllers;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import com.example.springboot.datajpa.app.models.entity.Factura;
import com.example.springboot.datajpa.app.models.entity.Producto;
import com.example.springboot.datajpa.app.models.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

  private final IClienteService clienteService;

  public FacturaController(IClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @GetMapping("/form/{clienteId}")
  public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash) {

    if (!clienteService.exists(clienteId)) {
      flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
      return "redirect:/listar";
    }

    Cliente cliente = clienteService.findOne(clienteId);

    Factura factura = new Factura();
    factura.setCliente(cliente);

    model.put("factura", factura);
    model.put("titulo", "Crear Factura");

    return "factura/form";
  }

  @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
  public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
    return clienteService.findByNombre(term);
  }
}
