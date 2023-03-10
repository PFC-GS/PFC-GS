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


Creada logica de negocio en service
Creados metodos toDto y toEntity en UsuarioDto pero con return null para que no de error