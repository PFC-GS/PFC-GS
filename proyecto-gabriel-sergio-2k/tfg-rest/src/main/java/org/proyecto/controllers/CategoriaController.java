package org.proyecto.controllers;

import org.proyecto.Entity.Categoria;
import org.proyecto.controllers.dto.CategoriaDto;
import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para las categorias
 */
@RestController
public class CategoriaController {
    @Autowired
    private GeneratorService service;

    /**
     * Devuelve todas las categorias
     *
     * @return Lista de categorias
     */
    @GetMapping(path = "/categorias")
    public ResponseEntity<List<CategoriaDto>> listAllCategorias() {
        return ResponseEntity.ok(service.getAllCategorias()
                .stream()
                .map(CategoriaDto::toDto)
                .collect(Collectors.toList()));
    }

    /**
     * Devuelve una categoria por su id
     *
     * @param categoriaId Id de la categoria
     * @return Categoria
     */
    @GetMapping(path = "/categorias/{categoriaId}")
    public ResponseEntity<CategoriaDto> getCategoriaById(
            @PathVariable("categoriaId") Integer categoriaId) {
        Categoria categoria = service.getCategoriaById(categoriaId);
        if (categoria != null) {
            return ResponseEntity.ok(CategoriaDto.toDto(categoria));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea una categoria
     *
     * @param categoria Categoria a crear
     * @return Categoria creada
     */
    @PostMapping(path = "/categorias")
    public ResponseEntity<Void> createCategoria(
            @Valid @RequestBody CategoriaDto categoria
    ) {
        if (service.addCategoria(CategoriaDto.toEntity(categoria))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Actualiza una categoria
     *
     * @param categoria Categoria a actualizar
     * @return Categoria actualizada
     */
    @PutMapping(path = "/categorias/{categoriaId}")
    public ResponseEntity<Void> updateCategoria(
            @Valid @RequestBody CategoriaDto categoria
    ) {
        if (service.updateCategoria(CategoriaDto.toEntity(categoria))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Borra una categoria
     *
     * @param categoriaId Id de la categoria a borrar
     * @return Categoria borrada
     */
    @DeleteMapping(path = "/categorias/{categoriaId}")
    public ResponseEntity<Void> deleteCategoria(
            @PathVariable("categoriaId") Integer categoriaId
    ) {
        if (service.deleteCategoria(categoriaId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
