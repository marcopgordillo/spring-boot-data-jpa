package com.example.springboot.datajpa.app.models.service;

import java.util.List;

import com.example.springboot.datajpa.app.models.entity.Cliente;

public interface IClienteService {
	
	 	List<Cliente> findAll();

	  void save(Cliente cliente);
	  
	  Cliente findOne(Long id);
	  
	  void delete(Long id);
}
