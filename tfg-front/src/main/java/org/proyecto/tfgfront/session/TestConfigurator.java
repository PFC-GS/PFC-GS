package org.proyecto.tfgfront.session;

import org.proyecto.tfgfront.model.Pregunta;

import java.util.List;

/**
 * Clase para configurar el test
 */
public class TestConfigurator {
    private static int usuarioId;
    private static int categoriaTest;

    private static int numeroPreguntas = 5;

    private static String fecha;
    private static List<Pregunta> respuestas;

    /**
     * Devuelve el id del usuario
     * @return Id del usuario
     */
    public static int getUsuarioId() {
        return usuarioId = Session.getUsuario().getId();
    }

    /**
     * Devuelve la categoria del test
     * @return Categoria del test
     */
    public static int getCategoriaTest() {
        return categoriaTest;
    }

    /**
     * Devuelve el numero de preguntas
     * @return Numero de preguntas
     */
    public static int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    /**
     * Devuelve la fecha
     * @return Fecha
     */
    public static String getfecha() {
        return fecha;
    }

    /**
     * Devuelve la lista de respuestas
     * @return Respuestas
     */
    public static List<Pregunta> getRespuestas() {
        return respuestas;
    }

    /**
     * Establece el numero de preguntas
     * @param numeroPreguntas Numero de preguntas
     */
    public static void setNumeroPreguntas(int numeroPreguntas) {
        TestConfigurator.numeroPreguntas = numeroPreguntas;
    }

    /**
     * Establece la fecha
     * @param fecha Fecha
     */
    public static void setfecha(String fecha) {
        TestConfigurator.fecha = fecha;
    }

    /**
     * Establece la categoria del test
     * @param categoriaTest Categoria del test
     */
    public static void setCategoriaTest(int categoriaTest) {
        TestConfigurator.categoriaTest = categoriaTest;
    }

    /**
     * Establece las respuestas realizadas por el usuario
     * @param respuesta Respuestas
     */
    public static void setRespuestas(List<Pregunta> respuesta) {
        TestConfigurator.respuestas = respuesta;
    }
}
