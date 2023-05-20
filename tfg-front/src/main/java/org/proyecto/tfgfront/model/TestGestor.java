package org.proyecto.tfgfront.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGestor {
    private Test test;
    private Set<Pregunta> preguntasCorrectas;
    private String dificultad;

}
