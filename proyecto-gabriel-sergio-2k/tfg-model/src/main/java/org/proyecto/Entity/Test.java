package org.proyecto.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    private Integer id;
    private Usuario usuario;
    private double calificacion;
    private Timestamp fecha;
}
