package org.proyecto.tfgfront.session;

import org.proyecto.tfgfront.model.Usuario;

public class Session {

    private static Usuario usuario;


    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario user) {
        Session.usuario = user;
    }


}
