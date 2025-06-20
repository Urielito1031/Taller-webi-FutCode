package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.JugadorDTO;

import java.util.List;

public interface JugadorService{
   List<JugadorDTO>getAll();
   List<JugadorDTO>getAllByEquipoId(Long equipoId);
}
