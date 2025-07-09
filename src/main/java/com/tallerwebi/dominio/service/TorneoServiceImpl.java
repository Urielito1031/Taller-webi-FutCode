package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Torneo;

import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TorneoServiceImpl implements TorneoService{

   private final TorneoRepository torneoRepository;
   private final EquipoRepository equipoRepository;
   private final EquipoTorneoRepository equipoTorneoRepository;
   private final FechaRepository fechaRepository;
   private final PartidoRepository partidoRepository;


   @Autowired
   public TorneoServiceImpl(TorneoRepository torneoRepository,
                            EquipoRepository equipoRepository,
                            EquipoTorneoRepository equipoTorneoRepository,
                            FechaRepository fechaRepository,
                            PartidoRepository partidoRepository) {

      this.torneoRepository = torneoRepository;
      this.equipoRepository = equipoRepository;
      this.equipoTorneoRepository = equipoTorneoRepository;
      this.fechaRepository = fechaRepository;
      this.partidoRepository = partidoRepository;
   }


   @Override
   public List<TorneoDTO> getAll() {
      List<Torneo> torneos = torneoRepository.findAll();

      return torneos.stream()
            .map(Torneo::convertToDTO)
            .collect(Collectors.toList());
   }

   @Override
   public TorneoDTO getById(Long id){
      Torneo torneoById = torneoRepository.getById(id);
      if(torneoById == null){
         return null;
      }
      return torneoById.convertToDTO();
   }

   @Override @Transactional
   public Torneo obtenerTorneoConFechas(Long torneoId) {
      return torneoRepository.obtenerTorneoConFechas(torneoId);
   }

   @Override
   public List<EquipoTorneo> calcularTablaDePosiciones(List<Partido> partidos){
      Map<Long, EquipoTorneo> tablaDePosiciones = new HashMap<>();

      for (Partido partido : partidos) {
         Equipo local = partido.getEquipoLocal();
         Equipo visitante = partido.getEquipoVisitante();

         // putIfAbsent --> Si no se lo agrego previamente se agrega con su id y un EquipoTorneo vacio
         // asignaciones local
         tablaDePosiciones.putIfAbsent(local.getId(), new EquipoTorneo());
         EquipoTorneo equipoLocal = tablaDePosiciones.get(local.getId());
         if (equipoLocal.getEquipo() == null) equipoLocal.setEquipo(local);
         equipoLocal.actualizarConPartido(partido, true);

         // asignaciones local visitante
         tablaDePosiciones.putIfAbsent(visitante.getId(), new EquipoTorneo());
         EquipoTorneo equipoVisitante = tablaDePosiciones.get(visitante.getId());
         if (equipoVisitante.getEquipo() == null) equipoVisitante.setEquipo(visitante);
         equipoVisitante.actualizarConPartido(partido, false);
      }

      return tablaDePosiciones.values().stream()
              .sorted(Comparator.comparingInt(EquipoTorneo::getPuntos).reversed())
              .collect(Collectors.toList());
   }


}
