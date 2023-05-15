package org.proyecto.tfgfront.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {


    private Integer id;

    private int usuario;

    private double calificacion;

    private Timestamp fecha;

    private Set<Pregunta> Preguntas;

}
