package com.example.springboot.datajpa.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "serial")
  private Long id;

  private String nombre;
  private Float precio;

  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @PrePersist
  public void prePersist() {
    createAt = new Date();
  }

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

  public Float getPrecio() {
    return precio;
  }

  public void setPrecio(Float precio) {
    this.precio = precio;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }
}
