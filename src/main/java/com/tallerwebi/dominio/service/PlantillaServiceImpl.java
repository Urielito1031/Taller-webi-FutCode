package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.*;
import com.tallerwebi.presentacion.dto.EsquemaDTO;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantillaServiceImpl implements PlantillaService {


   @Override
   public Boolean save(EsquemaDTO formacion){
      return null;
   }

   @Override
   public Boolean validarFormacion(EsquemaDTO formacion){
      return null;
   }

   @Override
   public void asignarPosicionesYJugadores(EsquemaDTO formacion){

   }

   @Override
   public List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema){
      return List.of();
   }
}