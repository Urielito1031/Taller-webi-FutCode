package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Narracion;

import java.util.List;

public interface NarracionRepository {
    void guardar(Narracion narracion);
    List<Narracion> obtenerPorPartidoId(Long partidoId);
}
