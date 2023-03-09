package org.proyecto.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pregunta {

    private Integer id;
    private Categoria categoria;
    private Dificultad dificultad;
    private String pregunta;
    private String respuestaA;
    private String respuestaB;
    private String respuestaC;
    private String solucion;

}
