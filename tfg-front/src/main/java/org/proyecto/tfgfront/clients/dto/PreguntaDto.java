package org.proyecto.tfgfront.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDto {

    private Integer id;

    private int categoria;

    private int dificultad;

    private String enunciado;

    private String respuestaA;

    private String respuestaB;

    private String respuestaC;

    private String solucion;


}
