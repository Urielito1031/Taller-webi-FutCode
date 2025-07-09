package com.tallerwebi.dominio.model.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fecha")
public class Fecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Long numeroDeFecha;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    @OneToMany(mappedBy = "fecha", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Partido> partidos = new ArrayList<>();

    @Column(name = "simulada", nullable = false)
    private boolean simulada = false;


    public void agregarPartido(Partido partido) {
        partidos.add(partido);
        partido.setFecha(this);
    }

}
