package org.proyecto.tfgfront.session;

import org.proyecto.tfgfront.model.Usuario;

/**
 * Clase para guardar la sesión del usuario
 */
public class Session {

    private static Usuario usuario;

    /**
     * Devuelve el usuario
     * @return usuario
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * Guarda el usuario
     * @param user usuario
     */
    public static void setUsuario(Usuario user) {
        Session.usuario = user;
    }


}
