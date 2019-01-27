package com.example.springboot.datajpa.app.models.service;

import com.example.springboot.datajpa.app.models.dao.IClienteDao;
import com.example.springboot.datajpa.app.models.dao.IProductoDao;
import com.example.springboot.datajpa.app.models.entity.Cliente;
import com.example.springboot.datajpa.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	private final IClienteDao clienteDao;
	private final IProductoDao productoDao;
	
	public ClienteServiceImpl(IClienteDao clienteDao, IProductoDao productoDao) {
		this.clienteDao = clienteDao;
		this.productoDao = productoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>)clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
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
	public boolean exists(Long id) {
		return clienteDao.exists(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.delete(id);
	}

	@Override
	public List<Producto> findByNombre(String term) {
//		return productoDao.findByNombre(term);
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}
}
