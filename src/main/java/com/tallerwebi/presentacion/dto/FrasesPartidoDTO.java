package com.tallerwebi.presentacion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FrasesPartidoDTO {
//@Getter
//@Setter

    private Map<String, List<String>> frases;

    public FrasesPartidoDTO() {
        this.frases = new HashMap<>();
    }

    public Map<String, List<String>> getFrases() {
        return frases;
    }

    public void setFrases(Map<String, List<String>> frases) {
        this.frases = frases;
    }

    public List<String> getFrasesPorTipo(EventoPartido tipoEvento) {
        return frases.getOrDefault(tipoEvento.name(), new ArrayList<>());
    }

}
