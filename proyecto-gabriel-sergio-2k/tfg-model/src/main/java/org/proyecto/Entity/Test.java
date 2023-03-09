package org.proyecto.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test")
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @Column(name = "calificacion")
    private double calificacion;
    @Column(name = "fecha")
    private Timestamp fecha;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "test_pregunta",
            joinColumns = @JoinColumn(name = "test"),
            inverseJoinColumns = @JoinColumn(name = "pregunta"))
    private Set<Pregunta> preguntas;
}
