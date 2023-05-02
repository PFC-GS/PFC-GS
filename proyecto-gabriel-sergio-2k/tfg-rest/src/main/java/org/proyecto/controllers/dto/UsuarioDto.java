package org.proyecto.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proyecto.Entity.Usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Integer id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellidos;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private boolean admin;

    private List<TestDto> tests;

    private Set<CategoriaDto> categorias;


    public static Usuario toEntity(UsuarioDto dto) {
        return new Usuario(
                dto.getId(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getEmail(),
                dto.getPassword(),
                dto.isAdmin(),
                dto.getTests().stream().map(TestDto::toEntity).collect(Collectors.toList()),
                dto.getCategorias().stream().map(CategoriaDto::toEntity).collect(Collectors.toSet())
        );
    }


    public static UsuarioDto toDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.isAdmin(),
                usuario.getTests().stream().map(TestDto::toDto).collect(Collectors.toList()),
                usuario.getCategorias().stream().map(CategoriaDto::toDto).collect(Collectors.toSet())
        );
    }


}
