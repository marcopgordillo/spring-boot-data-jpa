package com.example.springboot.datajpa.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer cantidad;

  @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "producto_id") // No es necesario ya que se esta creando el producto_id en la misma table factura_items
  private Producto producto;

  public Double calcularImporte() {
    return cantidad * producto.getPrecio();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }
}
