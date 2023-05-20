package org.proyecto.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGestor {
    private Test test;
    private Set<Pregunta> preguntasCorrectas;
    private String dificultad;
}
