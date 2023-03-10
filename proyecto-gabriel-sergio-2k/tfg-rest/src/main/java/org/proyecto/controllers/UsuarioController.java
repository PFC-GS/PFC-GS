package org.proyecto.controllers;

import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @Autowired
    private GeneratorService service;

    /*
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        return ResponseEntity.ok(
                usuarioService.getAllUsuarios() // TODO: 09/03/2023 añadir parámetros de búsqueda
                        .stream()
                        .map(UsuarioDto::toDto) // TODO: 09/03/2023 crearDTO
                        .collect(Collectors.toList()));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(
            @PathVariable("id") Integer usuarioId
    ) {
        if (usuarioService.getUsuarioById(usuarioId) == null) { // TODO: 09/03/2023 crear método getUsuarioById
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioDto.toDto(usuarioService.getUsuarioById(usuarioId)));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDto> createUsuario(
           @RequestBody UsuarioDto usuario
    ) {
        if (usuarioService.addUsuario(UsuarioDto.toEntity(usuario))) { // TODO: 09/03/2023 crear método addUsuario
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
        if (usuarioService.updateUsuario(usuarioId, UsuarioDto.toEntity(usuario))) { // TODO: 09/03/2023 crear método updateUsuario
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable("id") Integer usuarioId
    ) {
        if (usuarioService.deleteUsuario(usuarioId)) { // TODO: 09/03/2023 crear método deleteUsuario
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

     */


}
