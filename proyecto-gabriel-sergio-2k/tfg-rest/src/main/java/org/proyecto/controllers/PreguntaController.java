package org.proyecto.controllers;

import org.proyecto.Entity.Pregunta;
import org.proyecto.controllers.dto.PreguntaDto;
import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PreguntaController {
    @Autowired
    private GeneratorService service;

    @GetMapping(path = "/preguntas")
    public ResponseEntity<List<PreguntaDto>> getAllPreguntas(
            @RequestParam(value = "dificultad", required = false) Integer dificultad,
            @RequestParam(value = "categoria", required = false) Integer categoria

    ) {
        return ResponseEntity.ok(service.getAllPreguntas(dificultad, categoria)
                .stream()
                .map(PreguntaDto::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/preguntas/{id}")
    public ResponseEntity<PreguntaDto> getPreguntaById(
            @PathVariable("id") Integer preguntaId
    ) {
        Pregunta pregunta = service.getPreguntaById(preguntaId);
        if (pregunta != null) {
            return ResponseEntity.ok(PreguntaDto.toDto(pregunta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/preguntas")
    public ResponseEntity<Void> createPregunta(
            @Valid @RequestBody PreguntaDto pregunta
    ) {
        if (service.addPregunta(PreguntaDto.toEntity(pregunta))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(path = "/preguntas/{id}")
    public ResponseEntity<Void> updatePregunta(
            @Valid @RequestBody PreguntaDto pregunta
    ) {
        if (service.updatePregunta(PreguntaDto.toEntity(pregunta))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/preguntas/{id}")
    public ResponseEntity<Void> deletePregunta(
            @PathVariable("id") Integer preguntaId
    ) {
        if (service.deletePregunta(preguntaId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
