package org.proyecto.service;

import org.proyecto.Entity.Usuario;
import org.proyecto.dao.PreguntaDAO;
import org.proyecto.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GeneratorService {
    @Autowired
    private PreguntaDAO preguntaDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;

    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.findAll();
    }

    public Usuario getUsuarioById(Integer usuarioId) {
        return usuarioDAO.findById(usuarioId).orElse(null);
    }

    public boolean addUsuario(Usuario usuario) {
        if (usuarioDAO.existsById(usuario.getId())) {
            return false;
        } else {
            usuarioDAO.save(usuario);
            return true;
        }
    }
    public boolean updateUsuario(Integer usuarioId, Usuario usuario) {
        if (usuarioDAO.existsById(usuarioId)) {
            usuario.setId(usuarioId);
            usuarioDAO.save(usuario);
            return true;
        } else {
            return false;
        }
    }
    public boolean deleteUsuario(Integer usuarioId) {
        if (usuarioDAO.existsById(usuarioId)) {
            usuarioDAO.deleteById(usuarioId);
            return true;
        } else {
            return false;
        }
    }

}
