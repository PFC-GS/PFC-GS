package org.proyecto.tfgfront.model;


import lombok.*;

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
@ToString
public class Test {
    private Integer id;
    private int usuario;
    private double calificacion;
    private String fecha;
    private Set<Pregunta> preguntas;


}
