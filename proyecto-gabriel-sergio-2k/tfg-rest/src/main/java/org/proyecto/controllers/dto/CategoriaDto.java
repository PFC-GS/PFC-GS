package org.proyecto.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proyecto.Entity.Categoria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {

    @Positive
    private Integer id;
    @NotBlank
    private String nombre;


    public static CategoriaDto toDto(Categoria categoria) {
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre()
        );
    }

    public static Categoria toEntity(CategoriaDto dto) {
        return new Categoria(
                dto.getId(),
                dto.getNombre(),
                new ArrayList<>());
    }
}
