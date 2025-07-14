package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;

import java.util.List;

public interface EquipoTorneoService{
    List<EquipoTorneo> obtenerEquiposPorIdTorneo(Long idTorneo);

    //Metodo para usar cuando selecciona unirse a un torneo
   void unirseTorneo(Long torneoId, Long equipoId);
   List<EquipoTorneoDTO> getAllByTorneoId(Long torneoId);
}
