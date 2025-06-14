package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.FormatoTorneo;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.repository.FormatoTorneoRepository;
import com.tallerwebi.dominio.repository.TorneoRepository;
import com.tallerwebi.presentacion.dto.FormatoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TorneoServiceImpl implements TorneoService{

   private final TorneoRepository repository;
   private final FormatoTorneoRepository repoFormatoTorneo;

   @Autowired
   public TorneoServiceImpl(TorneoRepository repository,FormatoTorneoRepository repoFormatoTorneo) {
      this.repository = repository;
      this.repoFormatoTorneo = repoFormatoTorneo;
   }





   @Override
   public List<TorneoDTO> getAll() {
      List<Torneo> torneos = repository.findAll();
      if(torneos == null || torneos.isEmpty()){
         return new ArrayList<>();
      }
      return torneos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
   }

   @Override
   public TorneoDTO getById(Long id){
      Torneo torneoById = repository.getById(id);
      if(torneoById == null){
         return null;
      }
      return convertToDTO(torneoById);
   }

   private TorneoDTO convertToDTO(Torneo torneo) {
      TorneoDTO dto = new TorneoDTO();
      dto.setId(torneo.getId());
      dto.setNombre(torneo.getNombre());
      dto.setEstado(torneo.getEstado());
      dto.setDescripcion(torneo.getDescripcion());

      FormatoTorneoDTO formatoDTO = new FormatoTorneoDTO();
      FormatoTorneo formato = torneo.getFormatoTorneo();
      formatoDTO.setTipo(formato.getTipo());
      dto.setFormatoTorneo(formatoDTO);

      return dto;
   }
}
