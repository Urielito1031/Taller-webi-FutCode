package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "jugador")
public class Jugador {
   @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @javax.validation.constraints.Size(max = 50)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 50)
   private String nombre;

   @javax.validation.constraints.Size(max = 50)
   @javax.validation.constraints.NotNull
   @Column(name = "apellido", nullable = false, length = 50)
   private String apellido;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen")
   private String imagen;

   @javax.validation.constraints.NotNull
   @Column(name = "edad", nullable = false)
   private Integer edad;

   @javax.validation.constraints.NotNull
   @Column(name = "numero_camiseta", nullable = false)
   private Integer numeroCamiseta;

   @javax.validation.constraints.NotNull
   @Column(name = "rating", nullable = false, precision = 4, scale = 1)
   private Double rating;

   @javax.validation.constraints.NotNull
   @Column(name = "lesionado", nullable = false)
   private Boolean lesionado = false;

   @javax.validation.constraints.NotNull
   @Column(name = "estado_fisico", nullable = false, precision = 5, scale = 2)
   private Double estadoFisico;


   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "pais_id")
   private Pais pais;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "rareza_jugador", nullable = false)
   private String rarezaJugador;

 @NotNull
 @Lob
 @Column(name = "posicion", nullable = false)
 private String posicion;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "equipo_id")
 private Equipo equipo;
 public JugadorDTO convertToDTO(Jugador jugador) {
  JugadorDTO dto = new JugadorDTO();
  dto.setId(jugador.getId());
  dto.setNombre(jugador.getNombre());
  dto.setApellido(jugador.getApellido());
  dto.setImagen(jugador.getImagen());
  dto.setEdad(jugador.getEdad());
  dto.setNumeroCamiseta(jugador.getNumeroCamiseta());
  dto.setRating(jugador.getRating());
  dto.setEstadoFisico(jugador.getEstadoFisico());
  dto.setLesionado(jugador.getLesionado());
  dto.setPaisOrigen(jugador.getPais());

  // String a enum
  dto.setRarezaJugador(RarezaJugador.valueOf(jugador.getRarezaJugador()));

  // String separado por coma a lista de enums
  List<PosicionEnum> posiciones = Arrays.stream(jugador.getPosicion().split(","))
      .map(PosicionEnum::valueOf)
      .collect(Collectors.toList());
  dto.setPosicionNatural(posiciones);

  if (jugador.getEquipo() != null) {
   EquipoDTO equipoDTO = new EquipoDTO(); // Este debe ser implementado
   dto.setEquipo(equipoDTO.convertFromEntity(jugador.getEquipo()));
  }

  return dto;
 }



}