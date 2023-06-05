package org.proyecto.service;

import org.proyecto.Entity.*;
import org.proyecto.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase Servicio
 */
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

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String examToolEmail;


    /////////////////////////////////////////////////////////////
//      CRUD USUARIO

    /**
     * Método que devuelve todos los usuarios
     *
     * @return List<Usuario>
     */
    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.findAll();
    }

    /**
     * Método que devuelve un usuario por su id
     *
     * @param usuarioId id del usuario
     * @return Usuario
     */
    public Usuario getUsuarioById(Integer usuarioId) {
        return usuarioDAO.findById(usuarioId).orElse(null);
    }

    /**
     * Método que comprueba si el usuario existe o no y lo guarda
     *
     * @param usuario usuario
     * @return Usuario existe o no
     */
    public boolean addUsuario(Usuario usuario) {
        if (usuarioDAO.findByEmail(usuario.getEmail()) != null) {
            return false;
        } else {
            usuarioDAO.save(usuario);
            return true;
        }
    }

    /**
     * Método que actualiza comprueba si el usuario existe o no y lo actualiza
     *
     * @param usuarioId id del usuario
     * @param usuario   usuario
     * @return Usuario existe o no
     */
    public boolean updateUsuario(Integer usuarioId, Usuario usuario) {
        if (usuarioDAO.existsById(usuarioId)) {
            usuario.setId(usuarioId);
            usuarioDAO.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si el usuario existe o no y lo borra
     *
     * @param usuarioId id del usuario
     * @return Usuario existe o no
     */
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

    /**
     * Método que devuelve todos los test
     *
     * @return List<Test>
     */
    public List<Test> getAllTest() {
        return testDao.findAll();
    }

    /**
     * Método que devuelve un test por su id
     *
     * @param testId id del test
     * @return Test
     */
    public Test getTestById(Integer testId) {
        return testDao.findById(testId).orElse(null);
    }

    /**
     * Método que comprueba si el test existe o no y lo guarda
     *
     * @param test test
     * @return Test existe o no
     */
    public boolean addTest(Test test) {
        if (testDao.existsById(test.getId())) {
            return false;
        } else {
            testDao.save(test);
            return true;
        }
    }

    /**
     * Método que comprueba si el test existe o no y lo actualiza
     *
     * @param test test
     * @return Test existe o no
     */
    public boolean updateTest(Test test) {
        if (testDao.existsById(test.getId())) {
            testDao.save(test);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si el test existe o no y lo borra
     *
     * @param testId id del test
     * @return Test existe o no
     */
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

    /**
     * Método que devuelve todas las preguntas
     *
     * @param dificultad de la pregunta
     * @param categoria  de la pregunta
     * @return List<Pregunta>
     */
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

    /**
     * Método que devuelve una pregunta por su id
     *
     * @param preguntaId id de la pregunta
     * @return Pregunta
     */
    public Pregunta getPreguntaById(Integer preguntaId) {
        return preguntaDAO.findById(preguntaId).orElse(null);
    }

    /**
     * Método que comprueba si la pregunta existe o no y la guarda
     *
     * @param pregunta pregunta
     * @return Pregunta existe o no
     */
    public boolean addPregunta(Pregunta pregunta) {
        if (preguntaDAO.existsById(pregunta.getId())) {
            return false;
        } else {
            preguntaDAO.save(pregunta);
            return true;
        }
    }

    /**
     * Método que comprueba si la pregunta existe o no y la actualiza
     *
     * @param pregunta pregunta
     * @return Pregunta existe o no
     */
    public boolean updatePregunta(Pregunta pregunta) {
        if (preguntaDAO.existsById(pregunta.getId())) {
            preguntaDAO.save(pregunta);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si la pregunta existe o no y la borra
     *
     * @param preguntaId id de la pregunta
     * @return Pregunta existe o no
     */
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

    /**
     * Método que devuelve todas las dificultades
     *
     * @return List<Dificultad>
     */
    public List<Dificultad> getAllDificultad() {
        return dificultadDAO.findAll();
    }

    /**
     * Método que devuelve una dificultad por su id
     *
     * @param dificultad id de la dificultad
     * @return Dificultad
     */
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

    /**
     * Método que devuelve todas las categorias
     *
     * @return List<Categoria>
     */
    public List<Categoria> getAllCategorias() {
        return categoriaDAO.findAll();
    }

    /**
     * Método que devuelve una categoria por su id
     *
     * @param categoriaId id de la categoria
     * @return Categoria
     */
    public Categoria getCategoriaById(Integer categoriaId) {
        return categoriaDAO.findById(categoriaId).orElse(null);
    }

    /**
     * Método que comprueba si la categoria existe o no y la guarda
     *
     * @param categoria categoria
     * @return Categoria existe o no
     */
    public boolean addCategoria(Categoria categoria) {
        if (categoriaDAO.existsById(categoria.getId())) {
            return false;
        } else {
            categoriaDAO.save(categoria);
            return true;
        }
    }

    /**
     * Método que comprueba si la categoria existe o no y la actualiza
     *
     * @param categoria categoria
     * @return Categoria existe o no
     */
    public boolean updateCategoria(Categoria categoria) {
        if (categoriaDAO.existsById(categoria.getId())) {
            categoriaDAO.save(categoria);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que comprueba si la categoria existe o no y la borra
     *
     * @param categoriaId id de la categoria
     * @return Categoria existe o no
     */
    public boolean deleteCategoria(Integer categoriaId) {
        if (categoriaDAO.existsById(categoriaId)) {
            categoriaDAO.deleteById(categoriaId);
            return true;
        } else {
            return false;
        }
    }

    /////////////////////////////////////////////////////////

    /**
     * Método que devuelve un usuario mediante su email y pass
     *
     * @param email del usuario
     * @param pass  del usuario
     * @return Usuario
     */
    public Usuario getUsuarioByEmailAndPass(String email, String pass) {
        Usuario user = usuarioDAO.findByEmailAndPassword(email, pass);
        if (user != null) {
            user.getTests().isEmpty();
            user.getCategorias().isEmpty();
            return usuarioDAO.findByEmailAndPassword(email, pass);
        } else return null;
    }

    // Obtener una lista de preguntas de una categoría (PASARÄ A SER MËTODO PRIVATE)

    /**
     * Método que devuelve un Set de preguntas de una categoría
     *
     * @param categoriaId  id de la categoría
     * @param numPreguntas número de preguntas que se quieren
     * @return Set<Pregunta>
     */
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


    /**
     * Método que crea un test con preguntas aleatorias de una categoría
     *
     * @param usuarioId    id del usuario
     * @param categoriaId  id de la categoría
     * @param numpreguntas número de preguntas que se quieren
     * @return Test
     */
    public Test createTest(Integer usuarioId, Integer categoriaId, Integer numpreguntas) {
        if (numpreguntas == null) {
            numpreguntas = 10;
        }
        Set<Pregunta> preguntas = preguntaDAO.findRandomQuestions(categoriaId, numpreguntas);
        preguntas.forEach(p -> p.setSolucion(""));
        Test newTest = new Test();
        newTest.setPreguntas(preguntas);
        newTest.setFecha(new Timestamp(System.currentTimeMillis()));
        Usuario usuario = usuarioDAO.findById(usuarioId).orElse(null);
        newTest.setUsuario(usuario);

        return newTest;
    }

    /**
     * Método que corrige un test y lo guarda en la bbdd
     *
     * @param test test a corregir
     */
    public void getCorreccion(Test test) {
        float puntos = 0;
        for (Pregunta p : test.getPreguntas()) {
            Pregunta pregunaCorrecta = preguntaDAO.findById(p.getId()).orElse(null);
            if (pregunaCorrecta.getSolucion().equals(p.getSolucion())) {
                puntos++;
            }
        }
        double nota = (puntos / test.getPreguntas().size()) * 10;
        int decimalPlaces = 2;
        nota = (double) (Math.round(nota * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces));

        test.setCalificacion(nota);
        testDao.save(test);
    }

    /**
     * Método que devuelve un testGestor por usuario y fecha
     *
     * @param usuarioId id del usuario
     * @param fecha     fecha del test
     * @return TestGestor
     */
    public TestGestor getTestByUserIdAndDate(Integer usuarioId, Timestamp fecha) {
        Test test = testDao.findTestByUserIdAndDate(usuarioId, fecha);
        TestGestor tg = new TestGestor();
        tg.setTest(test);
        Set<Pregunta> preguntasCorregidas = new HashSet<>();
        float puntos = 0;
        for (Pregunta p : test.getPreguntas()) {
            Pregunta pregunaCorrecta = preguntaDAO.findById(p.getId()).orElse(null);
            puntos += pregunaCorrecta.getDificultad().getId();
            preguntasCorregidas.add(pregunaCorrecta);
        }
        tg.setPreguntasCorrectas(preguntasCorregidas);
        String dificultad = calcularDificultad(puntos, preguntasCorregidas.size());
        tg.setDificultad(dificultad);
        return tg;
    }

    /**
     * Método que comprueba si el usuario existe o no y envia un correo con su contraseña
     *
     * @param email email del usuario
     * @return boolean
     */
    public boolean enviarCorreo(String email) {
        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario != null) {
            try {
                configCorreo(usuario.getEmail(), usuario.getPassword());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para limpiar el json que se recibe del cliente y obtener el email
     *
     * @param requestBody json que se recibe del cliente
     * @return String
     */
    public String limpiarJson(String requestBody) {
        String regex = "\"email\":\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestBody);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * Método que configura el correo que se va a enviar
     *
     * @param email    email del usuario
     * @param password contraseña del usuario
     * @throws MessagingException excepción
     */
    public void configCorreo(String email, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setFrom(examToolEmail);
        helper.setSubject("ExamTool: Recuperación de contraseña");

        // Crear el contenido del mensaje en formato HTML con la firma personalizada
        String signature = "<p>Atentamente.<br>" +
                "Admin, ExamTool<br>" +
                "Phone: 123456789<br>" +
                "Web: <a href=\"https://www.examTool.es\">www.examTool.es</a><br>" +
                "Email: <a href=\"mailto:support@ExamTool.com\">support@ExamTool.com</a></p>";

        String body = "Su contraseña es: " + password + "<br><br>" + signature;

        helper.setText(body, true);
        mailSender.send(message);
    }

    /**
     * Método que devuelve una lista de testGestor por usuario y calcula la dificultad
     *
     * @param usuarioId id del usuario
     * @return List<TestGestor>
     */
    public List<TestGestor> getTestByUserId(Integer usuarioId) {
        List<Test> testList = testDao.findTestByUserId(usuarioId);
        List<TestGestor> testGestorList = new ArrayList<>();

        for (Test test : testList) {
            TestGestor testGestor = new TestGestor();
            testGestor.setTest(test);
            Set<Pregunta> preguntasCorregidas = new HashSet<>();
            float puntos = 0;

            for (Pregunta pregunta : test.getPreguntas()) {
                Pregunta preguntaCorrecta = preguntaDAO.findById(pregunta.getId()).orElse(null);
                puntos += preguntaCorrecta.getDificultad().getId();
                preguntasCorregidas.add(preguntaCorrecta);
            }

            String dificultad = calcularDificultad(puntos, preguntasCorregidas.size());
            testGestor.setPreguntasCorrectas(preguntasCorregidas);
            testGestor.setDificultad(dificultad);

            testGestorList.add(testGestor);
        }

        return testGestorList;
    }

    /**
     * Método que extraido que calcula la dificultad de un test
     *
     * @param puntos       puntos del test
     * @param numPreguntas número de preguntas del test
     * @return String
     */
    private String calcularDificultad(float puntos, int numPreguntas) {
        float res = puntos / (numPreguntas * 3);
        if (res < 0.5) {
            return "Fácil";
        } else if (res >= 0.5 && res < 0.66) {
            return "Media";
        } else if (res >= 0.66) {
            return "Difícil";
        }
        return "";
    }
}

