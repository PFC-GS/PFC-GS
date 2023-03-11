package org.proyecto.controllers;

import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DificultadController {
    @Autowired
    private GeneratorService service;


    @GetMapping(path = "/dificultades")
    public ResponseEntity<List<String>> listAllDificultades() { // TODO: 11/03/2023 deberia de ser un DTO?
        return ResponseEntity.ok(service.getAllDificultad());
    }


}
