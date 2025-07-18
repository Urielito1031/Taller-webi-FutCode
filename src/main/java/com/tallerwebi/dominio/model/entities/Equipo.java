package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.presentacion.dto.EquipoDTO;
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

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen")
   private String imagen;

   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "esquema_id", nullable = false)
   private Esquema esquema;

   @OneToOne
   @JoinColumn(name = "usuario_id")
   private Usuario usuario;

   @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
   @JoinTable(name = "equipo_jugador", joinColumns = @JoinColumn(name = "equipo_id"), inverseJoinColumns = @JoinColumn(name = "jugador_id"))
   private List<Jugador> jugadores = new ArrayList<>();

   @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<FormacionEquipo> formacion = new ArrayList<>();

   public Double getRatingEquipoGeneral() {
      double total = 0;

      for (Jugador j : this.jugadores) {
         total += j.getRating();
      }

      return total / this.jugadores.size();
   }

   public Double calcularRatingDelOnceTitular(){
      if(this.formacion == null || this.formacion.isEmpty()){
         return 0.0;
      }
      Double total = 0.0;
      int cantidad = 0;
      for (FormacionEquipo fe : this.formacion) {
         Jugador jugador = fe.getJugador();
         System.out.println("Jugador: " + jugador.getNombre() + " - Rating: " + jugador.getRating());
         if(jugador != null){
            total += jugador.getRating();
            cantidad++; // opcional porque siempre deberia ser 11
         }
      }

      if(cantidad == 0){
         return 555.0;
      }else{
         return 600.0;
      }
   }

   public void addJugador(Jugador jugador) {
      if (jugadores == null) {
         jugadores = new ArrayList<>();
      }
      if (!jugadores.contains(jugador)) { // para evitar tener jugadores repetidos
         jugadores.add(jugador);
         jugador.getEquipos().add(this);
      }
   }

   public boolean hasJugadores() {
      return jugadores != null && !jugadores.isEmpty();
   }

   public EquipoDTO convertToDTO() {
      EquipoDTO dto = new EquipoDTO();
      dto.setId(this.id);
      dto.setNombre(this.nombre);
      dto.setImagen(this.imagen);

      if (this.usuario != null) {
         dto.setUsuarioId(this.usuario.getId());
      }

      return dto;
   }

   public static Equipo convertToEntity(EquipoDTO dto) {
      Equipo entity = new Equipo();
      entity.setId(dto.getId());
      entity.setNombre(dto.getNombre());
      entity.setImagen(dto.getImagen());

      Usuario usuario = new Usuario();
      usuario.setId(dto.getUsuarioId());
      entity.setUsuario(usuario);

      return entity;
   }
}