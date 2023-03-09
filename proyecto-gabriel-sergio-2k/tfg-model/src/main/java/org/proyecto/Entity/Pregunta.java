package org.proyecto.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "dificultad")
    private Dificultad dificultad;

    @Column(name = "pregunta")
    private String pregunta;
    @Column(name = "respuestaA")
    private String respuestaA;
    @Column(name = "respuestaB")
    private String respuestaB;
    @Column(name = "respuestaC")
    private String respuestaC;
    @Column(name = "solucion")
    private String solucion;

    @ManyToMany(mappedBy = "preguntas")
    private Set<Test> tests;

}
