package org.proyecto.controllers;

import org.proyecto.controllers.dto.DificultadDto;
import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para las dificultades
 */
@RestController
public class DificultadController {
    @Autowired
    private GeneratorService service;

    /**
     * Devuelve todas las dificultades
     *
     * @return Lista de dificultades
     */
    @GetMapping(path = "/dificultades")
    public ResponseEntity<List<DificultadDto>> listAllDificultades() {
        return ResponseEntity.ok(service
                .getAllDificultad()
                .stream()
                .map(DificultadDto::toDto)
                .collect(Collectors.toList())
        );
    }

    /**
     * Crea una dificultad
     *
     * @param dificultad Dificultad a crear
     * @return
     */
    @PostMapping(path = "/dificultades")
    public ResponseEntity<Void> createDificultad(
            @Valid @RequestBody DificultadDto dificultad
    ) {
        if (service.addDificultad(DificultadDto.toEntity(dificultad))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}
