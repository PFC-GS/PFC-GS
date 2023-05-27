package org.proyecto.service;

import org.proyecto.Entity.*;
import org.proyecto.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private String username;

    @Value("${spring.mail.password}")
    private String password;



    /////////////////////////////////////////////////////////////
//      CRUD USUARIO


    public List<Usuario> getAllUsuarios() {
        return usuarioDAO.findAll();
    }

    public Usuario getUsuarioById(Integer usuarioId) {
        return usuarioDAO.findById(usuarioId).orElse(null);
    }

    public boolean addUsuario(Usuario usuario) {
        if (usuarioDAO.findByEmail(usuario.getEmail()) != null) {
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
    public Test createTestWithQuestions(Integer usuarioId, Integer categoriaId, Integer numPreguntas) {
        Test newTest = new Test();
        newTest.setPreguntas(getPreguntasForTest(categoriaId, numPreguntas));
        Usuario usuario = usuarioDAO.findById(usuarioId).orElse(null);
        newTest.setUsuario(usuario);
        newTest.setFecha(new Timestamp(System.currentTimeMillis()));

        return newTest;


    }

    //  Retornar TEst con preguntas V2
    public Test createTestV2(Integer usuarioId, Integer categoriaId, Integer numpreguntas) {
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

    //  Correccion de test y guardado en la bbdd
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

    //  Obtener el testGestor por usuario y fecha
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
        float res = puntos / (preguntasCorregidas.size() * 3);
        String dificultad = "";
        if (res < 0.5) {
            dificultad = "Fácil";
        } else if (res >= 0.5 && res < 0.66) {
            dificultad = "Media";
        } else if (res >= 0.66) {
            dificultad = "difícil";
        }
        tg.setDificultad(dificultad);
        return tg;
    }


    public boolean enviarCorreo(String email) {
        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario != null) {
            try {
                sendListEmail(usuario.getEmail(), usuario.getPassword());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    // TODO: 26/05/2023 cambiar este metodo por algo mas sencillo 
    public String limpiarCorreo(String requestBody) {
        String regex = "(?i)(?<=email\":\")(.*?)(?=\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(requestBody);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public void sendListEmail(String email, String password) throws MessagingException {
        // Configuración de las propiedades de JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Credenciales de autenticación
        String username = this.username;
        String userPassword = this.password;

        // Autenticación
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, userPassword);
            }
        };

        // Crear sesión de JavaMail
        Session session = Session.getInstance(props, auth);

        // Crear el mensaje de correo electrónico
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("ExamTool: Recuperación de contraseña");

        // Crear el contenido del mensaje en formato HTML con la firma personalizada
        String signature = "<p>Atentamente,<br>" +
                "Admin, ExamTool<br>" +
                "Web: <a href=\"https://www.examTool.es\">www.examTool.es</a><br>" +
                "Email: <a href=\"mailto:supportAdmin@ExamTool.com\">supportAdmin@ExamTool.com</a></p>";

        String body = "Su contraseña es: " + password + "<br><br>" + signature;
        message.setContent(body, "text/html");

        // Enviar el mensaje de correo electrónico
        Transport.send(message);
    }
}

