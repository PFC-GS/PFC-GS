He agregado las entidades segun el modelo creado en el drive,
ahora bien, la entidad que se genera del many to many no se si habria que crearla,
igualmente hablamos, este readme es para que vayamos poniendo las modificaciones que vayamos haciendo
en el proyecto, para que no se nos olvide nada.


He borrado la entidad que crearía el many to many y he añadido carpetas al rest, he añadido el 
aplication properties con los datos de la anterior práctica pero la base de datos la he llamado generator 
y he hecho las relaciones de las entidades.


Agregado package controller a rest con la dependencia de spring boot starter web
       - Endpoints de usuario
                  + GetMapping de todos los usuarios ()
                  + GetMapping de un usuario ()
                  + PostMapping de un usuario ()
                  + PutMapping de un usuario ()
                  + DeleteMapping de un usuario () 
Agregado package service con clase UsuarioService
Agregado package dto con usuarioDto

falta implementar los metodos de los endpoints


Agregadas las etiquetas de spring al service y los dao, agregadas dependencias 
de jaxb, además de cambiar de nombre al service para que sea más genérico de nuestro proyecto (no solo de los usuarios).

Para que el rest arranque hay que tener el docker levantado y en dbeaver 
crear la base de datos "generator" y establecer la conexión. El mainRest arranca :D

He modificado algunas dependencias de pom para que me funcionara el programa y he agregado el modulo model a rest para
que se pueda usar en el DAO


Creada logica de negocio en service de usuario
Creados metodos toDto y toEntity en UsuarioDto pero con return null para que no de error

Creados los controllers y la logica de negocio de test y de pregunta

Deberiamos de replantearnos como hacer el tema de la dificultad, porque es mejor que en vez de filtrar por dificultad
(ya que es una movida saber que preguntas son mas dificiles que otras) ademas que no le veo mucho sentido, que 
tenga esa funcionalidad..
el usuario pueda elegir entre preguntas especificas de 1 tema o combinadas de varios temas. 

Agregado controller y logica de Categoria

Agregados los dto, cambiado el campo "pregunta" en la entidad Pregunta por el campo "enunciado" además de 
introducir requestParam en el método de obtener todas las preguntas para obtenerlas por dificultad y/o categoría.

He agregado el campo password a Usuario y hecho los cambios necesarios debido a ello, los endpoints de login y la obtención 
de preguntas para realizar un test.

Agregado el campo admin en la entidad y dto de usuario 

Modificado entidades para agregar fetch de tipo lazy, 

Eliminado la etiqueta @Positive del id de los dto para no tener que introducir el id y la base de datos lo genere, 
actualizados también los campos de los dtos.

Creado el método para obtener un test con sus preguntas, para enviar a la parte del front, una vez realizado el test se enviará 
al servicio rest con la calificación para guardarse en la base de datos.