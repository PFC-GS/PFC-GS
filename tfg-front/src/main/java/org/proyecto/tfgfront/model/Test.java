package org.proyecto.tfgfront.model;


import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Test {
    private Integer id;
    private int usuario;
    private double calificacion;
    private  String fecha;
    private Set<Pregunta> preguntas;






}
