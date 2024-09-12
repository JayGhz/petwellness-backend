package com.petwellness.model.entity;

import com.petwellness.model.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre_producto", length = 50, nullable = false)
    private String nombreProducto;

    @Column(name = "imagen", length = 250, nullable = false)
    private String imagen;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Column(name = "costo", nullable = false)
    private Double costo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto", nullable = false)
    private TipoProducto tipoProducto;

    @Column(name = "stock", nullable = false)
    private Integer stock;

}