package com.petwellness.api;

import com.petwellness.dto.HorariosDisponiblesDTO;
import com.petwellness.service.HorariosDisponiblesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/horarios-disponibles")
public class HorariosDisponiblesController {
    private final HorariosDisponiblesService horariosDisponiblesService;
    @PreAuthorize("hasAnyRole('VETERINARIO')")
    @PostMapping
    public ResponseEntity<HorariosDisponiblesDTO> agregarHorario(@Valid @RequestBody HorariosDisponiblesDTO horarioDTO) {
        HorariosDisponiblesDTO nuevoHorario = horariosDisponiblesService.agregarHorario(horarioDTO);
        return new ResponseEntity<>(nuevoHorario, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('VETERINARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Integer id) {
        horariosDisponiblesService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    @GetMapping
    public ResponseEntity<List<HorariosDisponiblesDTO>> obtenerHorarios() {
        List<HorariosDisponiblesDTO> horarios = horariosDisponiblesService.obtenerHorarios();
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
    @GetMapping("/veterinario/{userId}")
    public ResponseEntity<List<HorariosDisponiblesDTO>> obtenerHorariosPorVeterinario(@PathVariable Integer userId) {
        List<HorariosDisponiblesDTO> horarios = horariosDisponiblesService.obtenerHorariosPorVeterinarioId(userId);
        return new ResponseEntity<>(horarios, HttpStatus.OK);
    }
}
