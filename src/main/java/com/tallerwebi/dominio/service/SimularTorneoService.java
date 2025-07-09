package com.tallerwebi.dominio.service;

import org.springframework.stereotype.Service;

@Service
public interface SimularTorneoService {
    void simularFecha(Long torneoId, Long numeroDeFecha);
}
