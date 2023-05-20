package org.proyecto.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proyecto.Entity.TestGestor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGestorDto {
    private TestDto test;
    private Set<PreguntaDto> preguntasCorrectas;
    private String dificultad;

    public static TestGestorDto toDto(TestGestor entity){
        return new TestGestorDto(
                TestDto.toDto(entity.getTest()),
                entity.getPreguntasCorrectas()
                        .stream()
                        .map(PreguntaDto::toDto)
                        .collect(Collectors.toSet()),
                entity.getDificultad()
        );
    }

}
