package com.petwellness.repository;

import com.petwellness.model.entity.CategoriaProducto;
import com.petwellness.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoriaProductoIn(List<CategoriaProducto> categoriasProducto);
}