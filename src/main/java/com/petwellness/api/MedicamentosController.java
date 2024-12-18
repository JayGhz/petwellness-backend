package com.petwellness.api;

import com.petwellness.dto.MedicamentosProfileDTO;
import com.petwellness.dto.MedicamentosRegistroDTO;
import com.petwellness.service.MedicamentosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/medicamentos")
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'VETERINARIO')")
public class MedicamentosController {

    private final MedicamentosService medicamentosService;

    @GetMapping
    @PreAuthorize("hasAnyRole('VETERINARIO','CUSTOMER')")
    public ResponseEntity<List<MedicamentosProfileDTO>> getAllMedicamentos() {
        List<MedicamentosProfileDTO> medicamentos = medicamentosService.getAllMedicamentos();
        return new ResponseEntity<>(medicamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('VETERINARIO','CUSTOMER')")
    public ResponseEntity<MedicamentosProfileDTO> getMedicamentoById(@PathVariable Integer id) {
        MedicamentosProfileDTO medicamento = medicamentosService.getMedicamentoById(id);
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('VETERINARIO')")
    public ResponseEntity<MedicamentosProfileDTO> createMedicamento(@Valid @RequestBody MedicamentosRegistroDTO medicamentosRegistroDTO) {
        MedicamentosProfileDTO newMedicamento = medicamentosService.createMedicamento(medicamentosRegistroDTO);
        return new ResponseEntity<>(newMedicamento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VETERINARIO')")
    public ResponseEntity<MedicamentosProfileDTO> updateMedicamento(
            @PathVariable Integer id,
            @Valid @RequestBody MedicamentosRegistroDTO medicamentosRegistroDTO) {
        MedicamentosProfileDTO updateMedicamento = medicamentosService.updateMedicamento(id, medicamentosRegistroDTO);
        return new ResponseEntity<>(updateMedicamento, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('VETERINARIO')")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Integer id) {
        medicamentosService.deleteMedicamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
