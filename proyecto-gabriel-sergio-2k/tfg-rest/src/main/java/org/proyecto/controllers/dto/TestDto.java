package org.proyecto.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proyecto.Entity.Pregunta;
import org.proyecto.Entity.Test;
import org.proyecto.Entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {


    private Integer id;
    @NotNull
    @Positive
    private int usuario;

    private double calificacion;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp fecha;
    private Set<PreguntaDto> preguntas;



    public static TestDto toDto(Test test) {
        return new TestDto(
                test.getId(),
                test.getUsuario().getId(),
                test.getCalificacion(),
                test.getFecha(),
                test.getPreguntas()
                        .stream()
                        .map(PreguntaDto::toDto)
                        .collect(Collectors.toSet())
        );
    }

    public static Test toEntity(TestDto dto) {
        Usuario usuario = new Usuario(dto.getUsuario(), null,null,null,null,false,null,null);
        return new Test(
                dto.getId(),
                usuario,
                dto.getCalificacion(),
                dto.getFecha(),
                dto.getPreguntas().stream().map(PreguntaDto::toEntity).collect(Collectors.toSet())
        );
    }
}
