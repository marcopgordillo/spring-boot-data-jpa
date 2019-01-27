package com.example.springboot.datajpa.app.models.dao;

import com.example.springboot.datajpa.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

  Usuario findByUsername(String username);

  @Query("from Usuario u where u.username = ?1")
  Usuario fetchByUsername(String username);
}
