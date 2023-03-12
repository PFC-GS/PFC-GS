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

@RestController
public class CategoriaController {
    @Autowired
    private GeneratorService service;

    @GetMapping(path = "/categorias")
    public ResponseEntity<List<CategoriaDto>> listAllCategorias() {
        return ResponseEntity.ok(service.getAllCategorias()
                .stream()
                .map(CategoriaDto::toDto)
                .collect(Collectors.toList()));
    }
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
