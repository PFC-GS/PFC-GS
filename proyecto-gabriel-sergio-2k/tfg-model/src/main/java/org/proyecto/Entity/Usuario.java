package org.proyecto.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name= "admin")
    private boolean admin;

    @OneToMany(mappedBy = "usuario")
    private List<Test> tests;

    @ManyToMany(mappedBy = "usuarios")
    private Set<Categoria> categorias;
}
