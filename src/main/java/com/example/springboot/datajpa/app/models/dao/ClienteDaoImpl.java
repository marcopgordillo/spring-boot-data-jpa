package com.example.springboot.datajpa.app.models.dao;

import com.example.springboot.datajpa.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClienteDaoImpl implements IClienteDao {

  @PersistenceContext
  private EntityManager em;

  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = true)
  public List<Cliente> findAll() {
    return em.createQuery("from Cliente").getResultList();
  }
}
