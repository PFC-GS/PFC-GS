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
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {


    private Integer id;
    @NotBlank
    private String nombre;
    private Set<UsuarioDto> usuarios;


    public static CategoriaDto toDto(Categoria categoria) {
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getUsuarios().stream().map(UsuarioDto::toDto).collect(Collectors.toSet())
        );
    }

    public static Categoria toEntity(CategoriaDto dto) {
        return new Categoria(
                dto.getId(),
                dto.getNombre(),
                new ArrayList<>(),
                dto.getUsuarios().stream().map(UsuarioDto::toEntity).collect(Collectors.toSet())
        );
    }
}
