package controller;

/**
 * Created by juancho on 04/04/16.
 */



import data_model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import db_helper.Db_connection;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;


public class Reception_controller
{
    @FXML private TextField id_text_field;
    @FXML private TextField name_text_field;
    @FXML private TextField last_name_text_field;
    @FXML private TextField direction_text_field;
    @FXML private TextField telephone_text_field;
    @FXML private TextField gender_text_field;
    @FXML private DatePicker birth_date_date_picker;
    @FXML private Button new_appointment_button;
    @FXML private Button edit_appointment_button;
    @FXML private Button add_queue_button;
    @FXML private Button edit_patient_data_button;
    @FXML private Button save_patient_data_button;
    @FXML private Button add_payment_button;
    @FXML private Button new_patient_data_button;
    @FXML private Label status_label;

    @FXML private Pane pane;

    private boolean is_id = true;
    private boolean is_name = false;
    private boolean is_new_patient = false;
    private static Person person = new Person();

    private Long user_id;

    @FXML private Db_connection db = new Db_connection();


    public Long get_user_id()
    {
        return user_id;
    }

    public void set_user_id(Long user_id)
    {
        this.user_id = user_id;
    }


    @FXML protected  void handle_id_text_changed_action(ActionEvent event)
    {
        is_id = true;
        is_name = false;
    }

    @FXML protected  void handle_name_text_changed_action(ActionEvent event)
    {
        is_id = false;
        is_name = true;

        //search by name

    }



    @FXML protected void handle_search_button_action(ActionEvent event) throws SQLException
    {
        //System.out.print("ID from prevoius: " + get_user_id().toString() );
        //search by id
        if (is_id)
        {
            String id = id_text_field.getText();

            this.person = db.get_person_by_id(id);
            //patient exist
            if (person != null)
            {
                this.name_text_field.setText(person.get_name());
                this.last_name_text_field.setText(person.get_last_name());
                this.direction_text_field.setText(person.get_direction());
                this.gender_text_field.setText(person.get_gender());
                this.birth_date_date_picker.setValue(LocalDate.parse(person.get_birth_date().toString()));
                this.telephone_text_field.setText(person.get_phone_num());

                //System.out.print(person.get_birth_date().toString());
                this.status_label.setText("");
                this.edit_patient_data_button.setDisable(false);
                this.new_patient_data_button.setDisable(true);

                this.new_appointment_button.setDisable(false);


            }
            else
            {
                this.name_text_field.clear();
                this.last_name_text_field.clear();
                this.direction_text_field.clear();
                this.gender_text_field.clear();
                this.birth_date_date_picker.setValue(LocalDate.parse("2016-03-26"));
                this.telephone_text_field.clear();
                this.gender_text_field.clear();

                this.status_label.setText("Paciente no existe!");
                this.new_patient_data_button.setDisable(false);
                this.edit_patient_data_button.setDisable(true);

                this.new_appointment_button.setDisable(true);



            }


        }
    }

    @FXML protected  void handle_new_appointment_button_action  (ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/add_appointment_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        Appointment_controller controller = fxmlLoader.<Appointment_controller>getController();
        controller.set_user_id(user_id);
        controller.set_patient_id(person.get_id());

        pane.getChildren().setAll(root);

    }

    @FXML protected  void handle_edit_appointment_button_action (ActionEvent event)
    {


    }

    @FXML protected  void handle_add_queue_button_action (ActionEvent event)
    {


    }

    @FXML protected  void handle_edit_patient_data_button_action (ActionEvent event)
    {
        this.name_text_field.setDisable(false);
        this.last_name_text_field.setDisable(false);
        this.direction_text_field.setDisable(false);
        this.telephone_text_field.setDisable(false);
        this.gender_text_field.setDisable(false);
        this.birth_date_date_picker.setDisable(false);

        this.save_patient_data_button.setDisable(false);

    }

    @FXML protected  void handle_add_payment_button_action (ActionEvent event)
    {


    }

    @FXML protected  void handle_save_patient_data_button_action(ActionEvent event)
    {
        //this.person = new Person();
        //this.person.set_id(Long.parseLong(id_text_field.getText()));
        this.person.set_name(name_text_field.getText());
        this.person.set_last_name(last_name_text_field.getText());
        this.person.set_direction(direction_text_field.getText());
        this.person.set_phone_num(telephone_text_field.getText());
        this.person.set_gender(gender_text_field.getText());
        this.person.set_birth_date(Date.valueOf(birth_date_date_picker.getValue()));

        if (this.is_new_patient)
        {
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
            this.status_label.setText("¡Pacietne gaurdado con éxito!");

            //disable camps
            this.name_text_field.setDisable(true);
            this.last_name_text_field.setDisable(true);
            this.direction_text_field.setDisable(true);
            this.telephone_text_field.setDisable(true);
            this.gender_text_field.setDisable(true);
            this.birth_date_date_picker.setDisable(true);

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

            this.person.set_id(Long.parseLong(id_text_field.getText()));

            this.status_label.setText("¡Editado con éxito!");
        }
    }


    @FXML protected  void handle_new_patient_button_action(ActionEvent event)
    {
        this.is_new_patient = true;

        this.handle_edit_patient_data_button_action(new ActionEvent());
        this.new_patient_data_button.setDisable(true);
        this.edit_patient_data_button.setDisable(true);

    }


}
