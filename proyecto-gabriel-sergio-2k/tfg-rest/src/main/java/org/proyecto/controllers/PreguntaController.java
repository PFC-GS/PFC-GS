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

/**
 * Controlador para las preguntas
 */
@RestController
public class PreguntaController {
    @Autowired
    private GeneratorService service;

    /**
     * Devuelve todas las preguntas
     *
     * @param dificultad dificultad de la pregunta (opcional)
     * @param categoria  categoria de la pregunta (opcional)
     * @return Lista de preguntas
     */
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

    /**
     * Devuelve una pregunta por su id
     *
     * @param preguntaId Id de la pregunta
     * @return Pregunta
     */
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

    /**
     * Crea una pregunta
     *
     * @param pregunta Pregunta a crear
     * @return Pregunta creada
     */
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

    /**
     * Actualiza una pregunta
     *
     * @param pregunta Pregunta a actualizar
     * @return Pregunta actualizada
     */
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

    /**
     * Elimina una pregunta
     *
     * @param preguntaId Id de la pregunta a eliminar
     * @return Pregunta eliminada
     */
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
