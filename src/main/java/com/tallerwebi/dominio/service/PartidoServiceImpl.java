package com.tallerwebi.dominio.service;


import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.repository.PartidoRepository;
import com.tallerwebi.presentacion.dto.PartidoDTO;
import com.tallerwebi.presentacion.dto.PartidoHistorialDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartidoServiceImpl implements  PartidoService{

   private final PartidoRepository partidoRepository;


   @Autowired
   public PartidoServiceImpl(PartidoRepository partidoRepository) {
      this.partidoRepository = partidoRepository;
   }

   @Override
   public void save(Partido partido){
      partidoRepository.save(partido);

   }
   @Override
   public List<PartidoDTO> obtenerPartidosPorEquipoId(Long idEquipo){
      if(!idIsValid(idEquipo)){
         return List.of();

      }
      return null;
   }

   @Override
   public List<PartidoHistorialDTO> obtenerPartidosJugadosPorEquipoId(Long idEquipo){
      if(!idIsValid(idEquipo)){
         return List.of();
      }
      List<Partido> partidos = this.partidoRepository.obtenerPartidosJugadosPorEquipoId(idEquipo);

      return partidos.stream()
              .map(partido ->
                PartidoHistorialDTO.ConvertToPartidoHistorialDTO(partido,idEquipo))
              .collect(Collectors.toList());
   }

   private boolean idIsValid(Long idEquipo){
      return idEquipo != null && idEquipo > 0;
   }

   @Override
   public List<PartidoDTO> obtenerPartidosPorTorneoId(Long idTorneo){
      return List.of();
   }

}
