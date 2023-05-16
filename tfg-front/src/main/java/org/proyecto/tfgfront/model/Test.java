package org.proyecto.tfgfront.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.security.cert.CertPath;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    private Integer id;
    private int usuario;
    private double calificacion;
    private String fecha;
    private Set<Pregunta> preguntas;


}
