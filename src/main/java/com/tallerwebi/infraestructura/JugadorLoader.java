package com.tallerwebi.infraestructura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;

@Getter
@Component
public class JugadorLoader {

    public List<JugadorDTO> cargarJugadoresDesdeJSON(){
        ObjectMapper mapper = new ObjectMapper(); // --> esto convierte los jugadores de JSON a objetos java
        TypeReference<List<JugadorDTO>> listaJugadores = new TypeReference<>() {}; // --> clase de JACKSON donde deserializa el JSON a un objeto java, la cual espera hacerlo dentro de la lista de jugadores
        InputStream input = getClass().getClassLoader().getResourceAsStream("jugadores.json"); // busca el archivo JSON
        try{
            return mapper.readValue(input, listaJugadores);
        } catch(Exception e){
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }

}
