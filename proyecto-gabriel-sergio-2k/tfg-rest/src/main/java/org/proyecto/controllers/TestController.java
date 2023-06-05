package org.proyecto.controllers;

import org.proyecto.Entity.Test;
import org.proyecto.Entity.TestGestor;
import org.proyecto.controllers.dto.TestDto;
import org.proyecto.controllers.dto.TestGestorDto;
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

/**
 * Controlador para los tests
 */
@RestController
public class TestController {
    @Autowired
    private GeneratorService service;

    /**
     * Devuelve todos los tests
     *
     * @return Lista de tests
     */
    @GetMapping(path = "/tests")
    public ResponseEntity<List<TestDto>> getAllTest() {
        return ResponseEntity.ok(service.getAllTest()
                .stream()
                .map(TestDto::toDto)
                .collect(Collectors.toList()));
    }

    /**
     * Devuelve todos los tests de un usuario
     *
     * @param testId Id del usuario
     * @return Lista de tests
     */
    @GetMapping(path = "/tests/{id}")
    public ResponseEntity<TestDto> getTestById(
            @PathVariable("id") Integer testId
    ) {
        Test test = service.getTestById(testId);
        if (test != null) {
            return ResponseEntity.ok(TestDto.toDto(test));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un test
     *
     * @param test Test a crear
     * @return Test creado
     */
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

    /**
     * Actualiza un test
     *
     * @param test Test a actualizar
     * @return Test actualizado
     */
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

    /**
     * Borra un test
     *
     * @param testId Id del test a borrar
     * @return Test borrado
     */
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

    /**
     * Devuelve todos los tests de un usuario
     *
     * @param usuarioId    Id del usuario
     * @param categoriaId  Id de la categoría
     * @param numpreguntas Número de preguntas
     * @return Lista de tests
     */

    @GetMapping(path = "/tests/preguntas2/{usuarioId}/{categoriaId}")
    public ResponseEntity<TestDto> getTestReady(
            @PathVariable("usuarioId") Integer usuarioId,
            @PathVariable("categoriaId") Integer categoriaId,
            @RequestParam(value = "numPreguntas", required = false) Integer numpreguntas
    ) {
        if (service.getCategoriaById(categoriaId) == null || service.getUsuarioById(usuarioId) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(TestDto.toDto(
                    service.createTest(usuarioId, categoriaId, numpreguntas)));
        }
    }

    /**
     * Introduce un test hecho por un usuario en la bbdd
     *
     * @param test Test a introducir
     * @return Test introducido
     */

    @PostMapping(path = "/test/correccion")
    public ResponseEntity<Void> getCorreccion(
            @Valid @RequestBody TestDto test
    ) {
        if (service.getUsuarioById(test.getUsuario()).getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            service.getCorreccion(TestDto.toEntity(test));
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Obtiene el último testGestor con el test de un usuario por fecha e id de usuario
     *
     * @param usuarioId Id del usuario
     * @param fecha     Fecha del test
     * @return TestGestor
     */

    @GetMapping(path = "/test/{usuarioId}/{fechaTest}")
    public ResponseEntity<TestGestorDto> getTestByUserIdAndDate(
            @PathVariable("usuarioId") Integer usuarioId,
            @PathVariable("fechaTest") Timestamp fecha
    ) {
        if (service.getUsuarioById(usuarioId) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(TestGestorDto.toDto(service.getTestByUserIdAndDate(usuarioId, fecha)));
        }
    }

    /**
     * Obtiene todos los testGestor de un usuario
     *
     * @param usuarioId Id del usuario
     * @return Lista de testGestor
     */
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
