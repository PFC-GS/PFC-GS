package org.proyecto.tfgfront.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
