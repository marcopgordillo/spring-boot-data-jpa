package com.example.springboot.datajpa.app.models.entity;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String apellido;
  private String email;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;
  
  /*@PrePersist
  public void prePersist() {
  	createAt = new Date();
  }*/

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cliente cliente = (Cliente) o;
    return getId().equals(cliente.getId()) &&
            getNombre().equals(cliente.getNombre()) &&
            getApellido().equals(cliente.getApellido()) &&
            getEmail().equals(cliente.getEmail()) &&
            getCreateAt().equals(cliente.getCreateAt());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getNombre(), getApellido(), getEmail(), getCreateAt());
  }

  @Override
  public String toString() {
    return "Cliente{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", email='" + email + '\'' +
            ", CreateAt=" + createAt +
            '}';
  }
}
