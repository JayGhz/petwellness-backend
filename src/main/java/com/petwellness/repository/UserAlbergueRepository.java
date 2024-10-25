package com.petwellness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.petwellness.model.entity.Albergue;
import com.petwellness.model.entity.RegistroMascota;
import com.petwellness.model.enums.Especie;
import com.petwellness.model.enums.Genero;

@Repository
public interface UserAlbergueRepository extends JpaRepository<Albergue, Integer> {
    // Consultar mascotas asignadas a un albergue
    @Query("SELECT r FROM RegistroMascota r WHERE r.user.id = :userId")
    List<RegistroMascota> findRegistroMascotasByUserId(@Param("userId") Integer userId);

    // Listar mascotas de un albergue por filtro
    @Query("SELECT r FROM RegistroMascota r WHERE r.user.id = :userId AND (:nombre IS NULL OR r.nombre LIKE %:nombre%) AND (:especie IS NULL OR r.especie = :especie) AND (:genero IS NULL OR r.genero = :genero)")
    List<RegistroMascota> findAllWithFilters(
            @Param("userId") Integer userId, 
            @Param("nombre") String nombre,
            @Param("especie") Especie especie,
            @Param("genero") Genero genero);
}

