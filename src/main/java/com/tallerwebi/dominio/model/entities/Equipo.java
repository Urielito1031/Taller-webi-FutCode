package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.presentacion.dto.ClubDTO;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "equipo")
public class Equipo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "club_id")
   private Club club;

   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "esquema_id", nullable = false)
   private Esquema esquema;

   @OneToOne
   @JoinColumn(name = "usuario_id")
   private Usuario usuario;

   @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch =FetchType.EAGER)
   private List<Jugador> jugadores = new ArrayList<>();

   public Double getRatingEquipo(){
      double total = 0;

      for (Jugador j : this.jugadores) {
         total += j.getRating();
      }

      return total/this.jugadores.size();
   }

   public void addJugador(Jugador jugador) {
      if (jugadores == null) {
         jugadores = new ArrayList<>();
      }
      jugadores.add(jugador);
      jugador.setEquipo(this);
   }

   // Método helper para remover jugadores de forma segura
   public void removeJugador(Jugador jugador) {
      if (jugadores != null) {
         jugadores.remove(jugador);
         jugador.setEquipo(null);
      }
   }

   // Método para limpiar jugadores de forma segura
   public void clearJugadores() {
      if (jugadores != null) {
         // Desasociar todos los jugadores primero
         for (Jugador jugador : new ArrayList<>(jugadores)) {
            jugador.setEquipo(null);
         }
         jugadores.clear();
      }
   }


   // Método para verificar si tiene jugadores
   public boolean hasJugadores() {
      return jugadores != null && !jugadores.isEmpty();
   }

   public EquipoDTO convertToDTO() {
      EquipoDTO dto = new EquipoDTO();
      dto.setId(this.id);
      dto.setNombre(this.nombre);


      if (this.club != null) {
         ClubDTO clubDTO = new ClubDTO();
         clubDTO.setId(this.club.getId());
         clubDTO.setNombre(this.club.getNombre());
         clubDTO.setPais(this.club.getPais());
         clubDTO.setImagen(this.club.getImagen());

         dto.setClub(clubDTO);
      }

      if (this.usuario != null) {
         dto.setUsuarioId(this.usuario.getId());
      }

      return dto;
   }

   public static Equipo convertToEntity(EquipoDTO dto) {
      Equipo entity = new Equipo();
      entity.setId(dto.getId());
      entity.setNombre(dto.getNombre());

      Usuario usuario = new Usuario();
      usuario.setId(dto.getUsuarioId());
      entity.setUsuario(usuario);

      if (dto.getClub() != null) {
         Club club = new Club();
         club.setId(dto.getClub().getId());
         club.setNombre(dto.getClub().getNombre());
         club.setImagen(dto.getClub().getImagen());
         entity.setClub(club);
      }

      return entity;
   }

   public String toString(){
      return "ID: " + this.id + "\n Nombre: " + this.nombre +
              "\n Club: " + (this.club != null ? this.club.getNombre() : "No asignado") +
              "\n Esquema: " + (this.esquema != null ? this.esquema.getEsquema() : "No asignado") +
              "\n Jugadores: " + (this.jugadores != null ? this.jugadores.size() : 0);
   }

}