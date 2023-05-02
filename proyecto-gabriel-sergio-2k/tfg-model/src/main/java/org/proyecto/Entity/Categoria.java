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
@Table(name = "categoria")
public class Categoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Pregunta> preguntas;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "usuario_categoria",
            joinColumns = @JoinColumn(name = "categoria"),
            inverseJoinColumns = @JoinColumn(name = "usuario"))
    private Set<Usuario> usuarios;
}
