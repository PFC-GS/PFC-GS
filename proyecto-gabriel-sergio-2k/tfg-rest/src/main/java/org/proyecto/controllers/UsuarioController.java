package org.proyecto.controllers;

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
public class UsuarioController {
    @Autowired
    private GeneratorService service;


    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        return ResponseEntity.ok(
                service.getAllUsuarios()
                        .stream()
                        .map(UsuarioDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(
            @PathVariable("id") Integer usuarioId
    ) {
        if (service.getUsuarioById(usuarioId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioDto.toDto(service.getUsuarioById(usuarioId)));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDto> createUsuario(
           @RequestBody UsuarioDto usuario
    ) {
        if (service.addUsuario(UsuarioDto.toEntity(usuario))) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Void> updateUsuario(
            @PathVariable("id") Integer usuarioId,
            @Valid @RequestBody UsuarioDto usuario
    ) {
        if (service.updateUsuario(usuarioId, UsuarioDto.toEntity(usuario))) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable("id") Integer usuarioId
    ) {
        if (service.deleteUsuario(usuarioId)) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




}
