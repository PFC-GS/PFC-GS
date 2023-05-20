package org.proyecto.tfgfront.session;

public class TestConfigurator {
    private static int usuarioId;
    private static int categoriaTest;

    private static int numeroPreguntas = 4;

    private static String fecha;

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

    public static void setNumeroPreguntas(int numeroPreguntas) {
        TestConfigurator.numeroPreguntas = numeroPreguntas;
    }

    public static void setfecha(String fecha) {
        TestConfigurator.fecha = fecha;
    }

    public static void setCategoriaTest(int categoriaTest) {
        TestConfigurator.categoriaTest = categoriaTest;
    }
}
