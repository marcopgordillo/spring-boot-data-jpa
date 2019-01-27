package com.example.springboot.datajpa.app.models.service;

import com.example.springboot.datajpa.app.models.dao.IUsuarioDao;
import com.example.springboot.datajpa.app.models.entity.Role;
import com.example.springboot.datajpa.app.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService") // Nombre del bean mismo nombre pero la primera con minuscula
public class JpaUserDetailsService implements UserDetailsService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final IUsuarioDao usuarioDao;

  public JpaUserDetailsService(IUsuarioDao usuarioDao) {
    this.usuarioDao = usuarioDao;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioDao.findByUsername(username);

    if (usuario == null) {
      logger.error("Error login: no existe el usuario '" + username + "'");
      throw new UsernameNotFoundException("Username " + username + " no existe en la base de datos!");
    }

    List<GrantedAuthority> authorities = new ArrayList<>();

    for (Role role : usuario.getRoles()) {
      logger.info("Role: ".concat(role.getAuthority()));
      authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    }

    if (authorities.isEmpty()) {
      logger.error("Error login: usuario '" + username + "' no tiene roles asignado!");
      throw new UsernameNotFoundException("Error login: usuario '" + username + "' no tiene roles asignado!");
    }

    return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
  }
}
