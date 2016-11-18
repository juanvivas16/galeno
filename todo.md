Primaria

->Ventana administrador, agregar a main_io_controller
-> Cambiar en la clase cita: marcador_cita por descripcion; eliminar  num_contacto; agregar: id_medico
-> agregar los campos "lista" a la clase cita, tipo entero
-> modificar tabla Historial, queda como sigue: id, id_consulta, id_pacinte, id_medico (usuario), fecha (visita), descripcion
-> eliminar items_historial
-> agregar el campo "pagada" y "terminad" a la consulta
-> agregar clave foranea "id_consulta" de la tabla Historia hacia la tabla Consulta
-> modificar el diagrama de clase segun el nuevo modelo de la base de datos (simplificadas las partes de recipe y examen)
-> idea modelo factura:
    -tener el nombre del item que se va a facturar y costo asociado.
    -en el ui tener una lista por si se va a cobrar mas de una cosa en la misma facturar.
    -cobrar la lista completa.

Secundaria

Pasar claves con hash
Sacar la "consulta SQL" de main_io_controller afuera de la funcion handle_login_button_action


Listos

Cambiar id_usuario de la tabla usuario por id_persona en EA y en informe y demas...
-> Agregar apellido a la tabla Persona (BD)
-> Citas proximas en recepcion_ui
-> Citas proximas en recepcion_ui
-> ADMINISTRADOR poder de editar todas las tablas
-> VALIDACION DE TODAS LAS ENTRADAS DESDE LA INTERFAZ
Tabuladores en orden correcto ( es decir que si estoy por ejm escribiendo nombre, al darle tab pasa a apellido y asi al campo siguiente)
