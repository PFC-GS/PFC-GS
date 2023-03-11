package org.proyecto.service;

import org.proyecto.Entity.Pregunta;
import org.proyecto.Entity.Test;
import org.proyecto.Entity.Usuario;
import org.proyecto.dao.PreguntaDAO;
import org.proyecto.dao.TestDao;
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
    @Autowired
    private TestDao testDao;

    /////////////////////////////////////////////////////////////
//      CRUD USUARIO


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

    /////////////////////////////////////////////////////////////
//      CRUD TEST


    public List<Test> getAllTest() {
        return testDao.findAll();
    }

    public Test getTestById(Integer testId) {
        return testDao.findById(testId).orElse(null);
    }

    public boolean addTest(Test test) {
        if (testDao.existsById(test.getId())) {
            return false;
        } else {
            testDao.save(test);
            return true;
        }
    }

    public boolean updateTest(Test test) {
        if (testDao.existsById(test.getId())) {
            testDao.save(test);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTest(Integer testId) {
        if (testDao.existsById(testId)) {
            testDao.deleteById(testId);
            return true;
        } else {
            return false;
        }
    }



    /////////////////////////////////////////////////////////////
//      CRUD PREGUNTA

    public List<Pregunta> getAllPreguntas() {
        return preguntaDAO.findAll();
    }

    public Pregunta getPreguntaById(Integer preguntaId) {
        return preguntaDAO.findById(preguntaId).orElse(null);
    }

    public boolean addPregunta(Pregunta pregunta) {
        if (preguntaDAO.existsById(pregunta.getId())) {
            return false;
        } else {
            preguntaDAO.save(pregunta);
            return true;
        }
    }

    public boolean updatePregunta(Pregunta pregunta) {
        if (preguntaDAO.existsById(pregunta.getId())) {
            preguntaDAO.save(pregunta);
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePregunta(Integer preguntaId) {
        if (preguntaDAO.existsById(preguntaId)) {
            preguntaDAO.deleteById(preguntaId);
            return true;
        } else {
            return false;
        }
    }

    /////////////////////////////////////////////////////////////
//      CRUD DIFICULTAD

        public List<String> getAllDificultad() {
            return Arrays.asList("Fácil", "Medio", "Difícil");
        }

        /////////////////////////////////////////////////////////////


}

