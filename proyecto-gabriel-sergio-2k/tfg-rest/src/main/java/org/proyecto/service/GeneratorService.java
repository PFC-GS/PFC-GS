package org.proyecto.service;

import org.proyecto.Entity.*;
import org.proyecto.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class GeneratorService {
    @Autowired
    private PreguntaDAO preguntaDAO;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private TestDao testDao;
    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private DificultadDAO dificultadDAO;

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

    public List<Pregunta> getAllPreguntas(Integer dificultad, Integer categoria) {
        if (dificultad != null && categoria == null) {
            return preguntaDAO.findByDificultad_Id(dificultad);
        } else if (dificultad == null && categoria != null) {
            return preguntaDAO.findByCategoria_Id(categoria);
        } else if (dificultad != null && categoria != null) {
            return preguntaDAO.findByDificultad_IdAndCategoria_Id(dificultad, categoria);
        } else {
            return preguntaDAO.findAll();
        }
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

    public List<Dificultad> getAllDificultad() {
        return dificultadDAO.findAll();
    }

    public boolean addDificultad(Dificultad dificultad) {
        if (dificultadDAO.existsById(dificultad.getId())) {
            return false;
        } else {
            dificultadDAO.save(dificultad);
            return true;
        }
    }


    /////////////////////////////////////////////////////////////
//     CRUD CATEGORIA

    public List<Categoria> getAllCategorias() {
        return categoriaDAO.findAll();
    }

    public Categoria getCategoriaById(Integer categoriaId) {
        return categoriaDAO.findById(categoriaId).orElse(null);
    }

    public boolean addCategoria(Categoria categoria) {
        if (categoriaDAO.existsById(categoria.getId())) {
            return false;
        } else {
            categoriaDAO.save(categoria);
            return true;
        }
    }

    public boolean updateCategoria(Categoria categoria) {
        if (categoriaDAO.existsById(categoria.getId())) {
            categoriaDAO.save(categoria);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCategoria(Integer categoriaId) {
        if (categoriaDAO.existsById(categoriaId)) {
            categoriaDAO.deleteById(categoriaId);
            return true;
        } else {
            return false;
        }
    }

    /////////////////////////////////////////////////////////
    // Obtener un usuario mediante su email y pass
    public Usuario getUsuarioByEmailAndPass(String email, String pass) {
        Usuario user = usuarioDAO.findByEmailAndPassword(email, pass);
        if (user != null) {
            user.getTests().isEmpty();
            user.getCategorias().isEmpty();
            return usuarioDAO.findByEmailAndPassword(email, pass);
        } else return null;
    }

    // Obtener una lista de preguntas de una categoría (PASARÄ A SER MËTODO PRIVATE)
    private Set<Pregunta> getPreguntasForTest(Integer categoriaId, Integer numPreguntas) {
        Set<Pregunta> preguntaList = new HashSet<>();
        if (numPreguntas == null) {
            numPreguntas = 10;
        }
        List<Pregunta> totalList = preguntaDAO.findByCategoria_Id(categoriaId);
        if (totalList.size() < numPreguntas) {   // Esta condición está para prevenir un bucle infinito si hubiera menos preguntas que las pedidas
            numPreguntas = totalList.size();
        }
        Random random = new Random();
        for (int i = 0; i < numPreguntas; i++) {
            int aux = random.nextInt(totalList.size());
            Pregunta pregunta = totalList.get(aux);
            if (!preguntaList.contains(pregunta)) {
                preguntaList.add(pregunta);
            } else {
                i--;
            }
        }
        return preguntaList;
    }

    // Retornar un Test con las preguntas
    public Test createTestWithQuestions(Usuario usuario, Integer categoriaId, Integer numPreguntas) {
        Test newTest = new Test();
        newTest.setPreguntas(getPreguntasForTest(categoriaId, numPreguntas));
        newTest.setUsuario(usuario);
        newTest.setFecha(new Timestamp(System.currentTimeMillis()));

        return newTest;


    }
}

