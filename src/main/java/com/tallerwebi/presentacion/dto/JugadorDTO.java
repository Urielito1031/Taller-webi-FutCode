package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.Pais;
import com.tallerwebi.dominio.model.PosicionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JugadorDTO {
   private Long id;
   private String nombre;
   private String apellido;
   private String imagen;
   private Integer edad;
   private Integer numeroCamiseta;
   private Double rating; // Ejemplo: 87.5
   private Boolean lesionado;
   private Double estadoFisico; // De 0.0 a 100.0, y varia segun cantidad de partidos
   private ClubDTO clubActual; // Puede ser null si es agente libre
   private List<PosicionEnum> posicionNatural;
   private Pais paisOrigen;

   public JugadorDTO() {}
   public JugadorDTO(Long id,String nombre, String apellido, String imagen, Integer edad,Integer numeroCamiseta, Double rating, Double estadoFisico, List<PosicionEnum> posicion, Pais paisOrigen) {
      this.id = id;
      this.nombre = nombre;
      this.apellido = apellido;
      this.imagen = imagen;
      this.edad = edad;
      this.numeroCamiseta = numeroCamiseta;
      this.rating = rating;
      this.estadoFisico = estadoFisico;
      this.posicionNatural = posicion;
      setLesionado(false);
      this.clubActual = null;
      this.paisOrigen = paisOrigen;
   }


}