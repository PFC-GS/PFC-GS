package org.proyecto.tfgfront.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Integer id;

    private String nombre;

    private String apellidos;

    private String email;

    private String password;

    private boolean admin;

    private List<TestDto> tests;


}
