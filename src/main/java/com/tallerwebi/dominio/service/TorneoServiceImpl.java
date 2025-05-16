package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorneoServiceImpl implements TorneoService{
   @Override
   public List<TorneoDTO> getAll() {
      return List.of();
   }
}
