He agregado las entidades segun el modelo creado en el drive,
ahora bien, la entidad que se genera del many to many no se si habria que crearla,
igualmente hablamos, este readme es para que vayamos poniendo las modificaciones que vayamos haciendo
en el proyecto, para que no se nos olvide nada.

S
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
