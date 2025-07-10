package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "narracion")
@Getter
@Setter
public class Narracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partido_id")
    private Partido partido;

    // Constructores, getters y setters
    public Narracion() {}
    public Narracion(String texto, Partido partido) {
        this.texto = texto;
        this.partido = partido;
    }
}
