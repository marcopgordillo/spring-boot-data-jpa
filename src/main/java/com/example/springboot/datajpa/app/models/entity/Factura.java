package com.example.springboot.datajpa.app.models.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String descripcion;

  private String observacion;

  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;

  @ManyToOne(fetch = FetchType.LAZY)
  private Cliente cliente;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "factura_id")
  private List<ItemFactura> items;

  public Factura() {
    items =  new ArrayList<>();
  }

  @PrePersist
  public void prePersist() {
    createAt = new Date();
  }

  public void addItemFactura(ItemFactura item) {
    items.add(item);
  }

  public Double getTotal() {
    Double total = 0.0;

    int size = items.size();

    for (int i = 0; i < size; i++) {
      total += items.get(i).calcularImporte();
    }

    return total;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<ItemFactura> getItems() {
    return items;
  }

  public void setItems(List<ItemFactura> items) {
    this.items = items;
  }
}
