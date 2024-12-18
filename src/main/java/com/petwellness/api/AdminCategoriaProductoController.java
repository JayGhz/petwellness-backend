package com.petwellness.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.petwellness.dto.CategoriaProductoDTO;
import com.petwellness.service.AdminCategoriaProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/categoria-producto")
public class AdminCategoriaProductoController {

        private final AdminCategoriaProductoService categoriaProductoService;

        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN, CUSTOMER')")
        public ResponseEntity<List<CategoriaProductoDTO>> getAllCategoriaProductos() {
                List<CategoriaProductoDTO> categorias = categoriaProductoService.getAll();
                return new ResponseEntity<>(categorias, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN, CUSTOMER')")
        public ResponseEntity<CategoriaProductoDTO> getCategoriaProductoById(@PathVariable("id") Integer id) {
                CategoriaProductoDTO categoria = categoriaProductoService.getById(id);
                return new ResponseEntity<>(categoria, HttpStatus.OK);
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @PostMapping
        public ResponseEntity<CategoriaProductoDTO> createCategoriaProducto(@Valid @RequestBody CategoriaProductoDTO categoriaProductoDTO) {
                CategoriaProductoDTO newCategoria = categoriaProductoService.create(categoriaProductoDTO);
                return new ResponseEntity<>(newCategoria, HttpStatus.CREATED);
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<CategoriaProductoDTO> updateCategoriaProducto(@PathVariable("id") Integer id, CategoriaProductoDTO categoriaProductoDTO) {
                CategoriaProductoDTO updatedCategoria = categoriaProductoService.update(id, categoriaProductoDTO);
                return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
        }

        @PreAuthorize("hasAnyRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCategoriaProducto(@PathVariable("id") Integer id) {
                categoriaProductoService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

}