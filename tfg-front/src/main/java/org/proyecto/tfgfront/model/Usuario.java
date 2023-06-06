package org.proyecto.tfgfront.model;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase modelo de Usuario
 */
public class Usuario {

    private Integer id;

    private String nombre;

    private String apellidos;

    private String email;

    private String password;

    private boolean admin;

    private List<Test> tests;

    private Set<Categoria> categorias;


}
