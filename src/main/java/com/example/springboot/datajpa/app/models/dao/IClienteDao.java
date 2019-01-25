package com.example.springboot.datajpa.app.models.dao;

import com.example.springboot.datajpa.app.models.entity.Cliente;

import java.util.List;

public interface IClienteDao {

  List<Cliente> findAll();

  void save(Cliente cliente);
  
  Cliente findOne(Long id);
}
