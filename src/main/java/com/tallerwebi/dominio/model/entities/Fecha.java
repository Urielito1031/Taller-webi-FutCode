package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "fecha")
public class    Fecha implements Comparable<Fecha>{
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

    @Override
    public int compareTo(Fecha o) {
        return this.numeroDeFecha.compareTo(o.numeroDeFecha);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fecha fecha = (Fecha) o;
        return simulada == fecha.simulada && Objects.equals(id, fecha.id) && Objects.equals(numeroDeFecha, fecha.numeroDeFecha) && Objects.equals(torneo, fecha.torneo) && Objects.equals(partidos, fecha.partidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroDeFecha, torneo, partidos, simulada);
    }

    public void agregarPartido(Partido partido) {
        partidos.add(partido);
        partido.setFecha(this);
    }
}
