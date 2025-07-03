package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipoServiceImpl implements EquipoService{

   private final EquipoRepository repository;
   private final RepositorioUsuario repositorioUsuario;

   @Autowired
   public EquipoServiceImpl(EquipoRepository repository,RepositorioUsuario repositorioUsuario) {
      this.repository = repository;
      this.repositorioUsuario = repositorioUsuario;
   }

   @Override
   public void save(EquipoDTO dto){
      if(!isValid(dto)){
         throw new IllegalArgumentException("El nombre no puede ser vacio");
      }
      Equipo equipoEntity = Equipo.convertToEntity(dto);

      repository.save(equipoEntity);
      if (dto.getUsuarioId() != null) {
         repositorioUsuario.asignarEquipoAUsuario(dto.getUsuarioId(), equipoEntity.getId());
      }

      dto.setId(equipoEntity.getId());
      System.out.println("Equipo guardado con éxito: " + equipoEntity);
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
