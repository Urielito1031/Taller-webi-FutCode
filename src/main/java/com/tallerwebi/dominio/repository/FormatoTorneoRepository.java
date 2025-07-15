package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.FormatoTorneo;
import com.tallerwebi.dominio.model.enums.TipoFormato;

public interface FormatoTorneoRepository {
   public void save(FormatoTorneo formatoTorneo);

   FormatoTorneo findByTipo(TipoFormato tipo);
}
