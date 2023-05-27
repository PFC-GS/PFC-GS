package org.proyecto.controllers;

import org.proyecto.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RecuperaPassController {
    @Autowired
    private GeneratorService service;

    @PostMapping("/recuperaPass")
    public ResponseEntity<String> enviarCorreo(@RequestBody String bodyRequest) {
        String email = service.limpiarCorreo(bodyRequest);
        boolean correoEnviado = service.enviarCorreo(email);
        if (correoEnviado) {
            return ResponseEntity.ok("Correo enviado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo enviar el correo");
        }
    }
}
