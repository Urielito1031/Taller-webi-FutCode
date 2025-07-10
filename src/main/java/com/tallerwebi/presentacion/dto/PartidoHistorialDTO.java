package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Partido;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PartidoHistorialDTO {
   private Long id;
   private EquipoDTO equipoLocal;
   private EquipoDTO equipoVisitante;
   private Long numeroDeFecha;
   private Integer golesLocal;
   private Integer golesVisitante;
   private String resultado;
   private String resultadoUsuario; // Nueva propiedad para el resultado desde la perspectiva del usuario
   private List<EventoPartidoDTO> eventosPartido;

   public PartidoHistorialDTO() {
      this.eventosPartido = new ArrayList<>();
   }

   public PartidoHistorialDTO(Long id, EquipoDTO equipoLocal, EquipoDTO equipoVisitante, Long numeroDeFecha) {
      this.id = id;
      this.equipoLocal = equipoLocal;
      this.equipoVisitante = equipoVisitante;
      this.numeroDeFecha = numeroDeFecha;
      this.eventosPartido = new ArrayList<>();
   }

   public static PartidoHistorialDTO ConvertToPartidoHistorialDTO(Partido partido, Long idEquipoUsuario) {
      if (partido == null) {
         return null;
      }

      PartidoHistorialDTO dto = new PartidoHistorialDTO();
      dto.setId(partido.getId());

      if (partido.getEquipoLocal() != null) {
         dto.setEquipoLocal(new EquipoDTO().convertFromEntity(partido.getEquipoLocal()));
      }

      if (partido.getEquipoVisitante() != null) {
         dto.setEquipoVisitante(new EquipoDTO().convertFromEntity(partido.getEquipoVisitante()));
      }

      if (partido.getFecha() != null) {
         dto.setNumeroDeFecha(partido.getFecha().getNumeroDeFecha());
      } else {
         dto.setNumeroDeFecha(null);
      }

      dto.setGolesLocal(partido.getGolesLocal());
      dto.setGolesVisitante(partido.getGolesVisitante());

      if (partido.getResultado() != null) {
         dto.setResultado(partido.getResultado().name());
         // Calcular el resultado desde la perspectiva del usuario
         if (partido.getResultado().name().equals("EMPATE")) {
            dto.setResultadoUsuario("EMPATASTE");
         } else{
            assert partido.getEquipoLocal() != null;
            if (partido.getEquipoLocal().getId().equals(idEquipoUsuario)) {
               // El usuario es el equipo local
               if (partido.getResultado().name().equals("LOCAL_GANA")) {
                  dto.setResultadoUsuario("GANASTE");
               } else if (partido.getResultado().name().equals("VISITANTE_GANA")) {
                  dto.setResultadoUsuario("PERDISTE");
               } else {
                  dto.setResultadoUsuario("PENDIENTE");
               }
            } else{
               assert partido.getEquipoVisitante() != null;
               if (partido.getEquipoVisitante().getId().equals(idEquipoUsuario)) {
                  // El usuario es el equipo visitante
                  if (partido.getResultado().name().equals("VISITANTE_GANA")) {
                     dto.setResultadoUsuario("GANASTE");
                  } else if (partido.getResultado().name().equals("LOCAL_GANA")) {
                     dto.setResultadoUsuario("PERDISTE");
                  } else {
                     dto.setResultadoUsuario("PENDIENTE");
                  }
               } else {
                  dto.setResultadoUsuario("PENDIENTE");
               }
            }
         }
      }

      dto.setEventosPartido(new ArrayList<>());

      return dto;
   }

   public String toString() {
      return "PartidoHistorialDTO{" +
        "id=" + id +
        ", equipoLocal=" + equipoLocal +
        ", equipoVisitante=" + equipoVisitante +
        ", numeroDeFecha=" + numeroDeFecha +
        ", golesLocal=" + golesLocal +
        ", golesVisitante=" + golesVisitante +
        ", resultado='" + resultado + '\'' +
        ", resultadoUsuario='" + resultadoUsuario + '\'' +
        ", eventosPartido=" + eventosPartido +
        '}';
   }
}