package org.proyecto.tfgfront.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {


    private Integer id;

    private int usuario;

    private double calificacion;

    private Timestamp fecha;

}
