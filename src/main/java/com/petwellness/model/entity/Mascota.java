package com.petwellness.model.entity;

import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;
import com.petwellness.model.enums.Raza;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @ManyToOne
    @JoinColumn(name = "usuarios_user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_usuario"), nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private Especie especie;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "raza", nullable = false)
    private Raza raza;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "foto", length = 250)
    private String foto;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "direccion", length = 250)
    private String direccion;

    @Column(name = "miembro_id", length = 8)
    private String miembroID;

    @Column(name = "titular_poliza", length = 50)
    private String titularPoliza;

    @Column(name = "info_adicional", length = 250)
    private String infoAdicional;
}
