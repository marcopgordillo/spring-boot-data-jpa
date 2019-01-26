package com.example.springboot.datajpa.app.models.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String nombre;
  
  @NotEmpty
  private String apellido;
  
  @NotEmpty
  @Email
  @Column(name="email", unique = true)
  private String email;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @ColumnDefault("''")
  private String foto;

  @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Factura> facturas;

  public Cliente() {
    facturas = new ArrayList<>();
  }

  public String getNombreCompleto() {
    return nombre + " " + apellido;
  }

  public void addFactura(Factura factura) {
    facturas.add(factura);
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

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public List<Factura> getFacturas() {
    return facturas;
  }

  public void setFacturas(List<Factura> facturas) {
    this.facturas = facturas;
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
            getCreateAt().equals(cliente.getCreateAt()) &&
            getFoto().equals(cliente.getFoto());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getNombre(), getApellido(), getEmail(), getCreateAt(), getFoto());
  }

  @Override
  public String toString() {
    return "Cliente{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", email='" + email + '\'' +
            ", CreateAt=" + createAt + '\'' +
            ", foto=" + foto +
            '}';
  }
}
