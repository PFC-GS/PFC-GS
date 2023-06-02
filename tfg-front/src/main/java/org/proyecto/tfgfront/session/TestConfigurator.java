package org.proyecto.tfgfront.session;

import org.proyecto.tfgfront.model.Pregunta;

import java.util.List;

public class TestConfigurator {
    private static int usuarioId;
    private static int categoriaTest;

    private static int numeroPreguntas = 5;

    private static String fecha;
    private static List<Pregunta> respuestas;

    public static int getUsuarioId() {
        return usuarioId = Session.getUsuario().getId();
    }
    public static int getCategoriaTest() {
        return categoriaTest;
    }
    public static int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public static String getfecha() {
        return fecha;
    }
    public static List<Pregunta> getRespuestas() {
        return respuestas;
    }



    public static void setNumeroPreguntas(int numeroPreguntas) {
        TestConfigurator.numeroPreguntas = numeroPreguntas;
    }

    public static void setfecha(String fecha) {
        TestConfigurator.fecha = fecha;
    }

    public static void setCategoriaTest(int categoriaTest) {
        TestConfigurator.categoriaTest = categoriaTest;
    }



    public static void setRespuestas(List<Pregunta> respuesta) {
        TestConfigurator.respuestas = respuesta;
    }
}
