package org.proyecto.controllers;

import org.proyecto.Entity.Test;
import org.proyecto.controllers.dto.TestDto;
import org.proyecto.controllers.dto.UsuarioDto;
import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {
    @Autowired
    private GeneratorService service;

    @GetMapping(path = "/tests")
    public ResponseEntity<List<TestDto>> getAllTest() {
        return ResponseEntity.ok(service.getAllTest()
                .stream()
                .map(TestDto::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/tests/{id}")
    public ResponseEntity<TestDto> getTestById(
            @PathVariable("id") Integer testId
    ) {
        Test test = service.getTestById(testId);
        if (test != null) {
            return ResponseEntity.ok(TestDto.toDto(test));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping(path = "/tests")
    public ResponseEntity<Void> createTest(
            @Valid @RequestBody TestDto test
    ) {
        if (service.addTest(TestDto.toEntity(test))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping(path = "/tests/{id}")
    public ResponseEntity<Void> updateTest(
            @Valid @RequestBody TestDto test
    ) {
        if (service.updateTest(TestDto.toEntity(test))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping(path = "/tests/{id}")
    public ResponseEntity<Void> deleteTest(
            @PathVariable("id") Integer testId
    ) {
        if (service.deleteTest(testId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // obtener un test con las preguntas cargadas (por defecto serán 10 preguntas)
    @GetMapping(path = "/tests/preguntas/{categoriaId}")
    public ResponseEntity<TestDto> getPreguntasForTest(
            @Valid @RequestBody UsuarioDto usuario,
            @PathVariable("categoriaId") Integer categoriaId,
            @RequestParam(value = "numPreguntas",required = false) Integer numPreguntas
    ){
        if (service.getCategoriaById(categoriaId) == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(TestDto.toDto(
                    service.createTestWithQuestions(UsuarioDto.toEntity(usuario),categoriaId,numPreguntas)));
        }
    }

    // Obtener un test con las preguntas, versión mejorada
    @GetMapping(path = "/tests/preguntas2/{categoriaId}")
    public ResponseEntity<TestDto> getTestReady(
            @Valid @RequestBody UsuarioDto usuario,
            @PathVariable("categoriaId") Integer categoriaId,
            @RequestParam(value = "numPreguntas",required = false) Integer numpreguntas
    ){
        if (service.getCategoriaById(categoriaId)==null || service.getUsuarioById(usuario.getId()) == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(TestDto.toDto(
                    service.createTestV2(UsuarioDto.toEntity(usuario),categoriaId,numpreguntas)));
        }
    }
    //  Obtener el test corregido
    @GetMapping(path = "/test/correccion")
    public ResponseEntity<TestDto> getCorreccion(
            @Valid @RequestBody TestDto test
    ){
        if (service.getUsuarioById(test.getUsuario()).getId() == null){
            return  ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(TestDto.toDto(
                    service.getCorreccion(TestDto.toEntity(test))));
        }
    }
}
