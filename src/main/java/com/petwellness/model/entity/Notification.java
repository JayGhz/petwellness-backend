package com.petwellness.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificaciones")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_notificacion_user"), nullable = false)
    private User user;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "leida")
    private boolean leida;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
