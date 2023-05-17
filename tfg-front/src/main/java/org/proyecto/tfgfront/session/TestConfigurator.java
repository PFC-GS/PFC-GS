package org.proyecto.tfgfront.session;

public class TestConfigurator {
    private static int usuarioId;
    private static int categoriaTest;

    private static int numeroPreguntas = 4;

    public static int getUsuarioId() {
        return usuarioId = Session.getUsuario().getId();
    }

    public static int getCategoriaTest() {
        return categoriaTest = 3;
    }

    public static int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public static void setNumeroPreguntas(int numeroPreguntas) {
        TestConfigurator.numeroPreguntas = numeroPreguntas;
    }
}
