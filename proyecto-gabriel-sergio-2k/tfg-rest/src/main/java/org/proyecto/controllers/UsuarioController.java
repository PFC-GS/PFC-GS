package org.proyecto.controllers;

import org.proyecto.Entity.Usuario;
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


    @GetMapping(path = "/usuarios")
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        return ResponseEntity.ok(
                service.getAllUsuarios()
                        .stream()
                        .map(UsuarioDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping(path = "/usuarios/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(
            @PathVariable("id") Integer usuarioId
    ) {
        Usuario usuario = service.getUsuarioById(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioDto.toDto(usuario));
    }

    @PostMapping(path = "/usuarios")
    public ResponseEntity<UsuarioDto> createUsuario(
            @RequestBody UsuarioDto usuario
    ) {
        if (service.addUsuario(UsuarioDto.toEntity(usuario))) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(path = "/usuarios/{id}")
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

    @DeleteMapping(path = "/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable("id") Integer usuarioId
    ) {
        if (service.deleteUsuario(usuarioId)) {
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Encontrar un usuario por su email y password
    @GetMapping(path = "/usuarios/{email}/{pass}")
    public ResponseEntity<UsuarioDto> getUsuarioByEmailAndPass(
            @PathVariable("email") String email,
            @PathVariable("pass") String pass
    ) {
        Usuario usuario = service.getUsuarioByEmailAndPass(email, pass);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(UsuarioDto.toDto(usuario));
        }
    }


}
