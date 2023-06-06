package org.proyecto.tfgfront.model;

import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase modelo de TestGestor
 */
public class TestGestor {
    private Test test;
    private Set<Pregunta> preguntasCorrectas;
    private String dificultad;


}
