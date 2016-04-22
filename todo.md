Primaria

->Ventana administrador, agregar a main_io_controller
-> Citas proximas en recepcion_ui
-> Cambiar en la clase cita: marcador_cita por descripcion; eliminar  num_contacto; agregar: id_medico
-> agregar los campos "lista" a la clase cita, tipo entero
-> modificar tabla Historial, queda como sigue: id, id_consulta, id_pacinte, id_medico (usuario), fecha (visita), descripcion
-> eliminar items_historial
-> agregar el campo "pagada" y "terminad" a la consulta
-> agregar clave foranea "id_consulta" de la tabla Historia hacia la tabla Consulta

Secundaria

Pasar claves con hash
Sacar la "consulta SQL" de main_io_controller afuera de la funcion handle_login_button_action






Listos

Cambiar id_usuario de la tabla usuario por id_persona en EA y en informe y demas...
-> Agregar apellido a la tabla Persona (BD)
