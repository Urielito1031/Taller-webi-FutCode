package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipoServiceImpl implements EquipoService{

   private final FormacionEquipoRepository formacionEquipoRepository;
   private final EquipoRepository repository;
   private final UsuarioService usuarioService;

   @Autowired
   public EquipoServiceImpl(EquipoRepository repository, UsuarioService usuarioService, FormacionEquipoRepository formacionEquipoRepository) {
      this.repository = repository;
      this.usuarioService = usuarioService;
      this.formacionEquipoRepository = formacionEquipoRepository;
   }

   @Override
   public void save(EquipoDTO dto){
      if(!isValid(dto)){
         throw new IllegalArgumentException("El nombre no puede ser vacio");
      }
      Equipo entity = Equipo.convertToEntity(dto);

      repository.save(entity);
   }

   @Override
   public void saveBoth(EquipoDTO equipoDTO, Usuario usuario) {
      if (!isValid(equipoDTO)) {
         throw new IllegalArgumentException("El nombre no puede ser vacío");
      }
      Equipo entity = Equipo.convertToEntity(equipoDTO);
      entity.setUsuario(usuario);

      Esquema esquema = new Esquema();
      esquema.setId(1L);
      entity.setEsquema(esquema);

      if (equipoDTO.getJugadores() != null && !equipoDTO.getJugadores().isEmpty()) {
         List<Jugador> jugadores = equipoDTO.getJugadores().stream()
           .map(jugadorDTO -> {
              Jugador jugador = Jugador.convertToEntity(jugadorDTO);
              jugador.getEquipos().add(entity);
              return jugador;
           })
           .collect(Collectors.toList());
         entity.setJugadores(jugadores);
      }

      repository.save(entity);

      usuario.setEquipo(entity);
      usuarioService.actualizar(usuario);
   }

   private List<Jugador> obtenerPlantilla(Equipo equipo){
      List<FormacionEquipo> formacionesDelEquipo = this.formacionEquipoRepository.findByEquipoId(equipo.getId());
      List<Jugador> jugadoresPlantillaEquipo = new ArrayList<>();
      for(FormacionEquipo fe : formacionesDelEquipo){
         jugadoresPlantillaEquipo.add(fe.getJugador());
      }
      return jugadoresPlantillaEquipo;
   }

   @Override
   public Double calcularRatingEquipo(Equipo equipo){
      Double rating = 0d;

      List<Jugador> jugadoresPlantilla = obtenerPlantilla(equipo);

      for(Jugador j : jugadoresPlantilla){
         rating += j.getRating();
      }
      return rating / jugadoresPlantilla.size();
   }


   private boolean isValid(EquipoDTO equipo){
      return !equipo.getNombre().trim().isEmpty();

   }

   @Override
   public List<EquipoDTO> getAll(){
      List<Equipo>equipos = repository.getAll();
      return equipos.stream().map(Equipo::convertToDTO).collect(Collectors.toList());
   }


   @Override
   public EquipoDTO getById(Long id){
      Equipo equipo = repository.getById(id);
      if(equipo == null){
         return null;
      }

      return equipo.convertToDTO();
   }

   @Override
   public EquipoDTO getByNombre(String nombre){

      return null;
   }


   @Override
   public void update(EquipoDTO equipo){

   }

   @Override
   public void delete(Long id){

   }

   @Override
   public Equipo sortearEquipoInicial() {
      return null;
   }

}