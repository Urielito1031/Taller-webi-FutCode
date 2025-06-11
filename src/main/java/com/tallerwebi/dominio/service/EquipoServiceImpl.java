package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoServiceImpl implements EquipoService{

   private final EquipoRepository repository;

   @Autowired
   public EquipoServiceImpl(EquipoRepository repository) {
      this.repository = repository;
   }
   @Override
   public void save(EquipoDTO dto){
      if(!isValid(dto)){
         throw new IllegalArgumentException("El nombre no puede ser vacio");
      }
      Equipo entity = Equipo.convertToEntity(dto);

      repository.save(entity);
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

}
