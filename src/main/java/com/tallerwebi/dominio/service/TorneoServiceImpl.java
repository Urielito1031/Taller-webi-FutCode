package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.presentacion.dto.FormatoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TorneoServiceImpl implements TorneoService{
   private List<TorneoDTO>torneos;

   public TorneoServiceImpl() {
      this.torneos = new ArrayList<>();
      cargarDatosDePrueba();
   }


   private void cargarDatosDePrueba() {
      FormatoTorneoDTO formatoLiga = new FormatoTorneoDTO();
      formatoLiga.setTipo(TipoFormato.LIGA);
      formatoLiga.setRondas(20);

      FormatoTorneoDTO formatoCopa = new FormatoTorneoDTO();
      formatoCopa.setTipo(TipoFormato.COPA);
      formatoCopa.setEquiposPorGrupo(4);
      formatoCopa.setEquiposQueAvanzan(2);

      // Crear torneos
      TorneoDTO torneoPrueba = new TorneoDTO();
      torneoPrueba.setNombre("Premier League");
      torneoPrueba.setFormatoTorneo(formatoLiga);
      torneoPrueba.setEstado(EstadoTorneoEnum.ABIERTO);

      TorneoDTO torneoLibertadores = new TorneoDTO();
      torneoLibertadores.setNombre("Libertadores");
      torneoLibertadores.setFormatoTorneo(formatoCopa);
      torneoLibertadores.setEstado(EstadoTorneoEnum.EN_CURSO);
      // Agregar a la lista
      this.torneos.add(torneoPrueba);
      this.torneos.add(torneoLibertadores);
   }
   @Override
   public List<TorneoDTO> getAll() {
      return this.torneos;
   }
}
