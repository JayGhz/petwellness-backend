package com.petwellness.service;

import com.petwellness.dto.VeterinarioDTO;
import com.petwellness.dto.VeterinarioRegistroDTO;

import java.util.List;

public interface VeterinarioService {
    VeterinarioRegistroDTO crearVeterinario(VeterinarioRegistroDTO veterinarioRegistroDTO);
    List<VeterinarioDTO> obtenerVeterinarios();
    void eliminarVeterinario(Integer id);

    VeterinarioDTO actualizarVeterinario(Integer id, VeterinarioDTO veterinarioActualizadoDTO);

    VeterinarioRegistroDTO actualizarVeterinario(Integer id, VeterinarioRegistroDTO veterinarioRegistroDTO);
}
