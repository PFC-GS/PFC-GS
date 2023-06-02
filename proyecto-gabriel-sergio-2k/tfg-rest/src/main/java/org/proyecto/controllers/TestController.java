package org.proyecto.controllers;

import org.proyecto.Entity.Test;
import org.proyecto.Entity.TestGestor;
import org.proyecto.controllers.dto.TestDto;
import org.proyecto.controllers.dto.TestGestorDto;
import org.proyecto.controllers.dto.UsuarioDto;
import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
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
/*
    // obtener un test con las preguntas cargadas (por defecto serán 10 preguntas)
    @GetMapping(path = "/tests/preguntas/{usuarioId}/{categoriaId}")
    public ResponseEntity<TestDto> getPreguntasForTest(
            @PathVariable("usuarioId") Integer usuarioId,
            @PathVariable("categoriaId") Integer categoriaId,
            @RequestParam(value = "numPreguntas",required = false) Integer numPreguntas
    ){
        if (service.getCategoriaById(categoriaId) == null || service.getUsuarioById(usuarioId) == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(TestDto.toDto(
                    service.createTestWithQuestions(usuarioId,categoriaId,numPreguntas)));
        }
    }
 */
    // Obtener un test con las preguntas, versión mejorada
    @GetMapping(path = "/tests/preguntas2/{usuarioId}/{categoriaId}")
    public ResponseEntity<TestDto> getTestReady(
            @PathVariable("usuarioId") Integer usuarioId,
            @PathVariable("categoriaId") Integer categoriaId,
            @RequestParam(value = "numPreguntas",required = false) Integer numpreguntas
    ){
        if (service.getCategoriaById(categoriaId)==null || service.getUsuarioById(usuarioId) == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(TestDto.toDto(
                    service.createTestV2(usuarioId,categoriaId,numpreguntas)));
        }
    }
    //  Introducir un test hecho por un usuario en la bbdd
    @PostMapping(path = "/test/correccion")
    public ResponseEntity<Void> getCorreccion(
            @Valid @RequestBody TestDto test
    ){
        if (service.getUsuarioById(test.getUsuario()).getId() == null){
            return  ResponseEntity.notFound().build();
        }else {
            service.getCorreccion(TestDto.toEntity(test));
            return ResponseEntity.ok().build();
        }
    }

    //  Obtener el último testGestor con el test de un usuario por fecha e id de usuario
    @GetMapping(path = "/test/{usuarioId}/{fechaTest}")
    public ResponseEntity<TestGestorDto> getTestByUserIdAndDate(
            @PathVariable("usuarioId") Integer usuarioId,
            @PathVariable("fechaTest") Timestamp fecha
    ){
        if (service.getUsuarioById(usuarioId)==null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(TestGestorDto.toDto(service.getTestByUserIdAndDate(usuarioId,fecha)));
        }
    }
    @GetMapping(path = "/test/{usuarioId}")
    public ResponseEntity<List<TestGestorDto>> getTestByUserId(
            @PathVariable("usuarioId") Integer usuarioId
    ) {
        if (service.getUsuarioById(usuarioId) == null) {
            return ResponseEntity.notFound().build();
        } else {
            List<TestGestor> testGestorList = service.getTestByUserId(usuarioId);
            List<TestGestorDto> testGestorDtoList = new ArrayList<>();

            for (TestGestor testGestor : testGestorList) {
                TestGestorDto testGestorDto = TestGestorDto.toDto(testGestor);
                testGestorDtoList.add(testGestorDto);
            }

            if (testGestorDtoList.isEmpty()) {
                return ResponseEntity.noContent().build();  // Retorna un código 204 si no hay tests
            } else {
                return ResponseEntity.ok(testGestorDtoList);  // Retorna los tests con un código 200
            }
        }
    }




}
