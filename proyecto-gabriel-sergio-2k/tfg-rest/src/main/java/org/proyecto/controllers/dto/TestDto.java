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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    @Positive
    private Integer id;
    @NotNull
    @Positive
    private int usuario;
    @Positive

    private double calificacion;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Timestamp fecha;




    public static TestDto toDto(Test test) {
        return new TestDto(
                test.getId(),
                test.getUsuario().getId(),
                test.getCalificacion(),
                test.getFecha()
        );
    }

    public static Test toEntity(TestDto dto) {
        Usuario usuario = new Usuario(dto.getUsuario(), null,null,null,null,null);
        return new Test(
                dto.getId(),
                usuario,
                dto.getCalificacion(),
                dto.getFecha(),
                null
        );
    }
}
