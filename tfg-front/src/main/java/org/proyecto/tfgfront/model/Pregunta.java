package org.proyecto.tfgfront.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {


    private Integer id;

    private int categoria;

    private int dificultad;

    private String enunciado;

    private String respuestaA;

    private String respuestaB;

    private String respuestaC;

    private String solucion;

}
