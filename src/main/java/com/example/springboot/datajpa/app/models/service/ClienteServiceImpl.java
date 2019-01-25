package com.example.springboot.datajpa.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.datajpa.app.models.dao.IClienteDao;
import com.example.springboot.datajpa.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Qualifier("clienteDaoJPA")
	private final IClienteDao clienteDao;
	
	public ClienteServiceImpl(IClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.delete(id);
	}
}
