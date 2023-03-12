package org.proyecto.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proyecto.Entity.Categoria;
import org.proyecto.Entity.Dificultad;
import org.proyecto.Entity.Pregunta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDto {

    @Positive
    private Integer id;
    @NotNull
    @Positive
    private int categoria;
    @NotNull
    @Positive
    private int dificultad;
    @NotBlank
    private String enunciado;

    private String respuestaA;

    private String respuestaB;

    private String respuestaC;
    @NotBlank
    private String solucion;


    public static PreguntaDto toDto(Pregunta pregunta) {
        return new PreguntaDto(
                pregunta.getId(),
                pregunta.getCategoria().getId(),
                pregunta.getDificultad().getId(),
                pregunta.getEnunciado(),
                pregunta.getRespuestaA(),
                pregunta.getRespuestaB(),
                pregunta.getRespuestaC(),
                pregunta.getSolucion()
        );
    }

    public static Pregunta toEntity(PreguntaDto dto) {
        Categoria categoria = new Categoria(dto.getCategoria(),null,null);
        Dificultad dificultad = new Dificultad(dto.getDificultad(), null,null);
        return new Pregunta(
                dto.getId(),
                categoria,
                dificultad,
                dto.getEnunciado(),
                dto.getRespuestaA(),
                dto.getRespuestaB(),
                dto.getRespuestaC(),
                dto.getSolucion(),
                null
        );
    }

}
