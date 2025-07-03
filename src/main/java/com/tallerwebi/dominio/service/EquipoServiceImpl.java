package com.tallerwebi.dominio.service;

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

   @Autowired
   public EquipoServiceImpl(EquipoRepository repository) {
      this.repository = repository;
   }

   @Override
   public void save(EquipoDTO dto){
      System.out.println("llego a metodo save de EquipoServiceImpl");
      if(!isValid(dto)){
         System.out.println("El nombre del equipo no puede ser vacio");
         throw new IllegalArgumentException("El nombre no puede ser vacio");
      }
      System.out.println("Validando equipo: " + dto.getNombre());
      Equipo entity = Equipo.convertToEntity(dto);

      System.out.println("Guardando equipo: " + entity.getNombre());
      repository.save(entity);
      System.out.println("Equipo guardado con éxito: " + entity.getId());
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
