package controller;

/**
 * Created by juancho on 04/04/16.
 */



import data_model.Appointment;
import data_model.Appointment_type;
import data_model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import db_helper.Db_connection;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase del controlador de la interfaz de recepción
 */
public class Reception_controller implements Initializable
{
    @FXML protected TextField id_text_field;
    @FXML private TextField name_text_field;
    @FXML private TextField last_name_text_field;
    @FXML private TextArea direction_text_field;
    @FXML private TextField telephone_text_field;
  //  @FXML private TextField gender_text_field;
    @FXML private DatePicker birth_date_date_picker;
    @FXML private Button new_appointment_button;
    @FXML private Button process_appointment_button;
    @FXML private Button add_queue_button;
    @FXML private Button edit_patient_data_button;
    @FXML private Button save_patient_data_button;
    @FXML private Button add_invoice_button;
    @FXML private Button new_patient_data_button;
    @FXML private Button cancel_button;
    @FXML private Label status_label;
    @FXML private ListView next_appointment_list_view;
    @FXML private ComboBox gender_combo_box;
    @FXML private Label user_name_label;

    @FXML private Pane pane;

    private boolean is_id = true;
    private boolean is_name = false;
    private boolean is_new_patient = false;
    private static Person person = new Person();

    private Long user_id = new Long(0);
    private Long appointment_id;

    @FXML private Db_connection db = new Db_connection();


    final Tooltip id_tooltip = new Tooltip();


    /**
     * Método inicializador del controlador
     * colocar el nombre del usuario actual
     * prepara y mostrar las consultas pendientes para el dia de hoy
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {


        id_text_field.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(9));
        name_text_field.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));
        last_name_text_field.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(20));
        birth_date_date_picker.addEventFilter(KeyEvent.KEY_TYPED, date_Validation(10));
        telephone_text_field.addEventFilter(KeyEvent.KEY_TYPED, telephone_Validation(11));
        direction_text_field.addEventFilter(KeyEvent.KEY_TYPED, direction_Validation(512));

        id_tooltip.setText("La cedula debe tener \n" +
                        "almenos 6 numeros y maximo 9\n" );

        id_text_field.setTooltip(id_tooltip);

        System.out.println("ID USuario");
        System.out.println(user_id);
        System.out.println(get_user_id());
        String qu_name = "SELECT p.name FROM User u JOIN Person p ON ' " + user_id.toString() + " ' = p.id GROUP BY name";
        ResultSet rsname = db.execute_query(qu_name);
        try
        {
            if (rsname.next())
            {
                user_name_label.setText(rsname.getString("name"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }




        //populate appointment list view
        java.util.Date date = new java.util.Date();

        String qu = "SELECT a.id, a.patient_id, a.doctor_id, a.description, a.type, a.date, a.time, p.name," +
                " p.last_name FROM Appointment a, Person p " +
                "WHERE a.doctor_id = p.id AND a.date = ' " + new Date(date.getTime()).toString() + " ' "; //'2016-04-20'

        System.out.print(new Date(date.getTime()).toString());
        ResultSet rss = db.execute_query(qu);
        List<Appointment> appointments_list = new ArrayList<Appointment>();

        try
        {
            if (rss != null)
            {
                while (rss.next())
                {
                    Appointment a = new Appointment();
                    a.set_id(rss.getLong("id"));
                    a.set_patient_id(rss.getLong("patient_id"));
                    a.set_doctor_id(rss.getLong("doctor_id"));
                    a.set_date(rss.getDate("date"));
                    a.set_time(rss.getTime("time"));
                    a.set_description(rss.getString("description"));
                    a.set_type(Appointment_type.valueOf(rss.getString("type")));

                    appointments_list.add(a);
                }
                ObservableList<Appointment> observable_appointment_list = FXCollections.observableArrayList(appointments_list);
                this.next_appointment_list_view.getItems().clear();
                this.next_appointment_list_view.setItems(observable_appointment_list);
                this.next_appointment_list_view.getSelectionModel().selectFirst();

            }

//            rsname.next();
//            user_name_label.setText(rsname.getString("name"));

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        //populate gender combo box
        ObservableList<String> type_list =
                FXCollections.observableArrayList(
                        "Masculino",
                        "Femenino"
                );

        this.gender_combo_box.getItems().clear();
        this.gender_combo_box.setItems(type_list);
        this.gender_combo_box.setDisable(true);
        this.direction_text_field.setDisable(true);

        this.gender_combo_box.getSelectionModel().selectFirst();


        // this.gender_combo_box.getSelectionModel().selectFirst();
    }



    /* Numeric Validation Limit the  characters to maxLengh AND to ONLY DigitS *************************************/
    public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh)
    {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= max_Lengh)
            {
                e.consume();
            }
            if(e.getCharacter().matches("[0-9.]")){
                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]"))
                {
                    e.consume();
                }
                else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]"))
                {
                    e.consume();
                }
            }
            else
            {
                e.consume();
            }
        };
    }


    /* Letters Validation Limit the  characters to maxLengh AND to ONLY Letters *************************************/
    public EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh)
    {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= max_Lengh)
            {
                e.consume();
            }
            if(e.getCharacter().matches("[A-Za-z ]"))
            {
            }
            else
            {
                e.consume();
            }
        };
    }


    /* Letters Validation Limit the  characters to maxLengh AND to ONLY Letters *************************************/
    public EventHandler<KeyEvent> direction_Validation(final Integer max_Lengh)
    {
        return e -> {
            TextArea txt_TextField = (TextArea) e.getSource();
            if (txt_TextField.getText().length() >= max_Lengh)
            {
                e.consume();
            }
            if(e.getCharacter().matches("[A-Za-z 0-9/]"))
            {
            }
            else
            {
                e.consume();
            }
        };
    }



    /* Letters Validation Limit the  characters to maxLengh AND to ONLY Letters *************************************/
    public EventHandler<KeyEvent> telephone_Validation(final Integer max_Lengh)
    {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= max_Lengh)
            {
                e.consume();
            }
            if(e.getCharacter().matches("[0-9]"))
            {
            }
            else
            {
                e.consume();
            }
        };
    }


    /* Letters Validation Limit the  characters to maxLengh AND to ONLY Letters *************************************/
    public EventHandler<KeyEvent> date_Validation(final Integer max_Lengh)
    {
        return e -> {
            DatePicker txt_TextField = (DatePicker) e.getSource();
            String str = txt_TextField.getValue().toString();

            System.out.println(str);
            if ( str.isEmpty() )
                return;

            if (txt_TextField.getValue().toString().length() > max_Lengh)
            {
                e.consume();
            }
            if (str.length() == max_Lengh)
            {
                // 3/26/2016
                if (str.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}") )
                {

                }
                else
                    txt_TextField.setValue(LocalDate.parse("1970-01-01"));
            }

            else if (e.getCharacter().matches("[0-9/]"))
            {
            }
            else
            {
                e.consume();
            }
        };
    }




    /**
     * Obtener el id del usuario actual del sistema
     * @return Long el id del usuario
     */
    public Long get_user_id()
    {
        return user_id;
    }

    /**
     * Asignar el id del usuario
     * @param user_id Long id de usuario
     */
    public void set_user_id(Long user_id)
    {
        this.user_id = user_id;
    }

    /**
     * Activar is_id cuando se cambia el texto en la busqueda
     * @param event
     */
    @FXML protected  void handle_id_text_changed_action(ActionEvent event)
    {
        is_id = true;
        is_name = false;
    }

    /**
     * Activar is_name cuando se cambia el texto en nombre
     * @param event
     */
    @FXML protected  void handle_name_text_changed_action(ActionEvent event)
    {
        is_id = false;
        is_name = true;

        //search by name

    }

    /**
     * Lógica del botón cancelar
     * desabilitar la operabilidad de algunos botones en la interfaz
     * @param event
     * @throws SQLException
     */
    @FXML protected  void handle_cancel_button_action(ActionEvent event) throws SQLException
    {
//        //this.handle_search_button_action(null);
//        this.name_text_field.setDisable(true);
//        this.last_name_text_field.setDisable(true);
//        this.direction_text_field.setDisable(true);
//        this.telephone_text_field.setDisable(true);
//       // this.gender_text_field.setDisable(true);
//        this.birth_date_date_picker.setDisable(true);
//        this.gender_combo_box.setDisable(true);
//
//        this.save_patient_data_button.setDisable(true);
        System.exit(0);
    }

    /**
     * Manjear la lógica y accion del boton Buscar
     * buscar la persona con el id especificado en id_text_field
     * y mostrar los detalles del usuario en los campos correspondientes.
     * @param event
     * @throws SQLException
     */
    @FXML protected void handle_search_button_action(ActionEvent event) throws SQLException
    {
        //System.out.print("ID from prevoius: " + get_user_id().toString() );
        //search by id

        if(id_text_field.getText().isEmpty() || ! checkID(Long.valueOf(id_text_field.getText())))
            status_label.setText("ID invalido.");

        else if (is_id)
        {

            this.add_invoice_button.setDisable(true);


            String id = id_text_field.getText();

            Person tmp_person = db.get_person_by_id(id);
            //this.person = db.get_person_by_id(id);

            //patient exist
            if (tmp_person != null)
            {
                this.person = tmp_person;
                this.name_text_field.setText(person.get_name());
                this.last_name_text_field.setText(person.get_last_name());
                this.direction_text_field.setText(person.get_direction());
                // this.gender_text_field.setText(person.get_gender());


                String temp1_gender = person.get_gender();

                if(temp1_gender.equals("M"))
                    this.gender_combo_box.getSelectionModel().selectFirst();
                else if (temp1_gender.equals("F"))
                    this.gender_combo_box.getSelectionModel().selectLast();


                this.birth_date_date_picker.setValue(LocalDate.parse(person.get_birth_date().toString()));
                this.telephone_text_field.setText(person.get_phone_num());

                //System.out.print(person.get_birth_date().toString());
                this.status_label.setText("");
                this.edit_patient_data_button.setDisable(false);
                this.new_patient_data_button.setDisable(true);

                this.new_appointment_button.setDisable(false);


                //todo check if the patient has appointment with done state, the enable the invoice button
                String query = "SELECT id FROM Appointment WHERE done = 1 AND patient_id = '" + id + "'";
                ResultSet rs = db.execute_query(query);
                if (rs.next())
                {
                    this.add_invoice_button.setDisable(false);
                    this.appointment_id = rs.getLong("id");
                }

            }
            else
            {
                this.name_text_field.clear();
                this.last_name_text_field.clear();
                this.direction_text_field.clear();
                //  this.gender_text_field.clear();
                this.birth_date_date_picker.setValue(LocalDate.parse("2016-03-26"));
                this.telephone_text_field.clear();
                this.gender_combo_box.getSelectionModel().clearSelection();


                this.status_label.setText("Paciente no existe!");
                this.new_patient_data_button.setDisable(false);
                this.edit_patient_data_button.setDisable(true);

                this.new_appointment_button.setDisable(true);

            }
        }
    }

    /**
     * Procesar el boton de neuva consulta
     * preparar y cargar la interfaz de consulta
     * @param event
     * @throws IOException
     */
    @FXML protected  void handle_new_appointment_button_action  (ActionEvent event) throws IOException
    {
        //prepear and load new fxml ui
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/add_appointment_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        //access another controller to pass parameters and populate list view
        Appointment_controller controller = fxmlLoader.<Appointment_controller>getController();
        controller.set_user_id(user_id);
        controller.initialize(null, null);
        controller.set_patient_id(person.get_id());
        controller.populate_appointment_list_view();

        Main.primary_stage.setTitle("Citas | Galeno (C) 2016");
        pane.getChildren().setAll(root);

    }


    @FXML protected  void handle_process_appointment_button_action (ActionEvent event)
    {


    }

    @FXML protected  void handle_add_queue_button_action (ActionEvent event)
    {


    }

    /**
     * Proceasr el boton para agregar nuevo paciente a la base de datos
     * descativar los campos donde se muestran los datos del pacietne recientemente agregado
     * @param event
     */
    @FXML protected  void handle_edit_patient_data_button_action (ActionEvent event)
    {
        this.name_text_field.setDisable(false);
        this.last_name_text_field.setDisable(false);
        this.direction_text_field.setDisable(false);
        this.telephone_text_field.setDisable(false);
       // this.gender_text_field.setDisable(false);
        this.birth_date_date_picker.setDisable(false);
        this.gender_combo_box.setDisable(false);

        this.save_patient_data_button.setDisable(false);

    }

    /**
     * Procesar la lógica del boton Factura
     * prepara la interfaz de factura
     * enviar el id del usuario actual del sistema
     * enviar el id de cita
     * enviar el id del paciente (persona)
     * @param event
     * @throws IOException
     */
    @FXML protected  void handle_add_invoice_button_action (ActionEvent event) throws IOException
    {
        //todo go to invoice ui and send appointment id and patient_id



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/invoice_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        //access another controller to pass parameters and populate list view
        Invoice_controller controller = fxmlLoader.<Invoice_controller>getController();
        controller.set_patient_id(this.person.get_id());
        controller.set_appointment_id(this.appointment_id);
        controller.set_user_id(this.user_id);


        pane.getChildren().setAll(root);


    }

    /**
     * Procesar la lógica del boton Guardar
     * Validar todos los campos de los datos del paciente, si pasa todas las pruebas
     * Guardar los datos editado del paciente (perona) en la base de datos
     * de lo contrario mostrar error en el estado (status_label)
     * Desactive los campos de textos luego de la modificación.
     * @param event
     */
    @FXML protected  void handle_save_patient_data_button_action(ActionEvent event)
    {
        //this.person = new Person();
        //this.person.set_id(Long.parseLong(id_text_field.getText()));


        if(checkAlpha(name_text_field.getText()))
            this.person.set_name(name_text_field.getText());
        else
            this.status_label.setText("¡Error en nombre!");

        if(checkAlpha(last_name_text_field.getText()))
            this.person.set_last_name(last_name_text_field.getText());
        else
            this.status_label.setText("¡Error en Apellido!");


        this.person.set_direction(direction_text_field.getText());

        if(checkNonAlpha(telephone_text_field.getText()))
            this.person.set_phone_num(telephone_text_field.getText());
        else
            this.status_label.setText("¡Error en Telefono!");


     //   this.person.set_gender(gender_text_field.getText());
        this.person.set_birth_date(Date.valueOf(birth_date_date_picker.getValue()));

        String temp_gender = this.gender_combo_box.getSelectionModel().getSelectedItem().toString();

        if(temp_gender.equals("Masculino"))
            this.person.set_gender("M");
        else if (temp_gender.equals("Femenino"))
            this.person.set_gender(("F"));

        if (this.is_new_patient)
        {
            if(checkNonAlpha(id_text_field.getText()) && checkID(Long.parseLong(id_text_field.getText())))
                this.person.set_id(Long.parseLong(id_text_field.getText()));

            this.person.set_reg_date(new Date(new java.util.Date().getTime() ));

            String query = "INSERT INTO Person VALUES (" + person.get_id() + "" +
                    ", '" + person.get_name() + "'" +
                    ", '" + person.get_last_name() + "'" +
                    ", '" + person.get_gender() + "'" +
                    ", '" + person.get_birth_date() + "'" +
                    ", '" + person.get_reg_date() + "'" +
                    ", '" + person.get_direction() + "'" +
                    ", '" + person.get_phone_num() + "'" +
                    ")";

            int a = db.execute_update(query);

            if(checkNonAlpha(telephone_text_field.getText()) && checkAlpha(name_text_field.getText()) && checkAlpha(last_name_text_field.getText()) && checkID(Long.parseLong(id_text_field.getText())))
                this.status_label.setText("¡Pacietne gaurdado con éxito!");
            else
                this.status_label.setText("Error al guardar. Verificar campos");


                //disable camps
                this.name_text_field.setDisable(true);
                this.last_name_text_field.setDisable(true);
                this.direction_text_field.setDisable(true);
                this.telephone_text_field.setDisable(true);
                this.birth_date_date_picker.setDisable(true);
                this.gender_combo_box.setDisable(true);
                this.save_patient_data_button.setDisable(true);
                this.new_patient_data_button.setDisable(true);
                this.edit_patient_data_button.setDisable(false);



        }
        else
        {
            String query = "UPDATE Person SET id=" + id_text_field.getText() +
                    ", name='" + person.get_name() + "'" +
                    ", last_name='" + person.get_last_name() + "'" +
                    ", gender = '" + person.get_gender() + "'" +
                    ", birth_date = '" + person.get_birth_date() + "'" +
                    ", direction = '" + person.get_direction() + "'" +
                    ", phone_num = '" + person.get_phone_num() + "'" +
                    "WHERE Person.id=" + person.get_id();
            int a = db.execute_update(query);

            if(checkNonAlpha(id_text_field.getText()) && checkID(Long.parseLong(id_text_field.getText())))
                this.person.set_id(Long.parseLong(id_text_field.getText()));

            if(checkNonAlpha(telephone_text_field.getText()) && checkAlpha(name_text_field.getText()) && checkAlpha(last_name_text_field.getText()) && checkID(Long.parseLong(id_text_field.getText())))
                this.status_label.setText("¡Editado con éxito!");
            else
                this.status_label.setText("Error al editar. Verificar campos");
        }

        this.id_text_field.setDisable(false);
    }

    /**
     * Procesar la lógica del boton Paciente Nuevo
     * habilitar los campos de datos para ingresar los datos del paciente(Persona)
     * desactivar los botones de nuevo paciente y editar datos mientras se crea el nuevo paciente.
     * @param event
     */
    @FXML protected  void handle_new_patient_button_action(ActionEvent event)
    {
        this.is_new_patient = true;

        this.gender_combo_box.getSelectionModel().selectFirst();


        this.handle_edit_patient_data_button_action(new ActionEvent());
        this.new_patient_data_button.setDisable(true);
        this.edit_patient_data_button.setDisable(true);
        this.id_text_field.setDisable(true);

    }

    /**
     * Verificar si una cadena es un número valido
     * @param s String cadena a verificar
     * @return boolean true si es número, de lo contrario false
     */
    public static boolean isNumeric(String s)
    {
        for(int i = 0; i < s.length(); ++i) {
            char caracter = s.charAt(i);

            if(!Character.isDigit(caracter)) {
                return false;
            }
        }
        return true;
    }

    //Usando expresiones regulaes

    //que solo sean numeros

    /**
     * Verificar que una cadena solo tenga números
     * @param str String cadena a verificar
     * @return boolean true si contiene solo numeros, de lo contrario false
     */
    public static boolean checkNonAlpha(String str)
    {
        boolean respuesta = false;

        if (str.matches("([0-9]|\\-)+"))
            respuesta = true;

        return respuesta;
    }

    /**
     * Verificar que una cadena sea de solo letras
     * @param str String cadena de entrada a verificar
     * @return boolean true si pasa la prueba, false de lo contrario
     */
    //que solo sean letras
    public static boolean checkAlpha(String str)
    {
        boolean respuesta = false;

        if (str.matches("([a-z]|[A-Z]| | \\s)+"))
            respuesta = true;

        return respuesta;
    }

    /**
     * Verificar el formato del ID (cedula)
     * @param id Long id de entrada  averificar
     * @return boolean true si es válido, de lo contrario false.
     */
    public static boolean checkID(Long id)
    {
        if( id > 500000 && id < 999999999 )
            return true;
        else
            return false;
    }


    @FXML
    protected void handle_menu_item_exit_action(ActionEvent e)
    {
        System.exit(0);
    }



}
