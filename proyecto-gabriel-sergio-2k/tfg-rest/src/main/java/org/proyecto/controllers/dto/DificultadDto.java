package org.proyecto.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proyecto.Entity.Dificultad;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DificultadDto {

    @Positive
    private Integer id;
    @NotBlank
    private String nombre;

    public static DificultadDto toDto(Dificultad dificultad){
        return new DificultadDto(
                dificultad.getId(),
                dificultad.getNombre());
    }
    public static Dificultad toEntity(DificultadDto dto){
        return new Dificultad(
                dto.getId(),
                dto.getNombre(),
                new ArrayList<>());
    }
}
