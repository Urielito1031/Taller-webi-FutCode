package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.TorneoDTO;

import java.util.List;

public interface TorneoService {
   List<TorneoDTO> getAll();
   TorneoDTO getById(Long id);
}
