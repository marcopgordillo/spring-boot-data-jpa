package com.example.springboot.datajpa.app.models.service;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import com.example.springboot.datajpa.app.models.entity.Factura;
import com.example.springboot.datajpa.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {
	
	List<Cliente> findAll();

	Page<Cliente> findAll(Pageable pageable);

	void save(Cliente cliente);
	  
	Cliente findOne(Long id);

	boolean exists(Long id);
	  
	void delete(Long id);

	List<Producto> findByNombre(String term);

	void saveFactura(Factura factura);
}
