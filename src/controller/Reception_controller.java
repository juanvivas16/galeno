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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import db_helper.Db_connection;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class Reception_controller implements Initializable
{
    @FXML protected TextField id_text_field;
    @FXML private TextField name_text_field;
    @FXML private TextField last_name_text_field;
    @FXML private TextField direction_text_field;
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

    @FXML private Pane pane;

    private boolean is_id = true;
    private boolean is_name = false;
    private boolean is_new_patient = false;
    private static Person person = new Person();

    private Long user_id;
    private Long appointment_id;

    @FXML private Db_connection db = new Db_connection();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

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
       // this.gender_combo_box.getSelectionModel().selectFirst();
    }




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

    @FXML protected  void handle_cancel_button_action(ActionEvent event) throws SQLException
    {
        //this.handle_search_button_action(null);
        this.name_text_field.setDisable(true);
        this.last_name_text_field.setDisable(true);
        this.direction_text_field.setDisable(true);
        this.telephone_text_field.setDisable(true);
       // this.gender_text_field.setDisable(true);
        this.birth_date_date_picker.setDisable(true);
        this.gender_combo_box.setDisable(true);

        this.save_patient_data_button.setDisable(true);
    }


    @FXML protected void handle_search_button_action(ActionEvent event) throws SQLException
    {
        //System.out.print("ID from prevoius: " + get_user_id().toString() );
        //search by id
        if (is_id)
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

    @FXML protected  void handle_new_appointment_button_action  (ActionEvent event) throws IOException
    {
        //prepear and load new fxml ui
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/add_appointment_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        //access another controller to pass parameters and populate list view
        Appointment_controller controller = fxmlLoader.<Appointment_controller>getController();
        controller.set_user_id(user_id);
        controller.set_patient_id(person.get_id());
        controller.populate_appointment_list_view();

        pane.getChildren().setAll(root);

    }

    @FXML protected  void handle_process_appointment_button_action (ActionEvent event)
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
       // this.gender_text_field.setDisable(false);
        this.birth_date_date_picker.setDisable(false);
        this.gender_combo_box.setDisable(false);

        this.save_patient_data_button.setDisable(false);

    }

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

    @FXML protected  void handle_save_patient_data_button_action(ActionEvent event)
    {
        //this.person = new Person();
        //this.person.set_id(Long.parseLong(id_text_field.getText()));
        this.person.set_name(name_text_field.getText());
        this.person.set_last_name(last_name_text_field.getText());
        this.person.set_direction(direction_text_field.getText());
        this.person.set_phone_num(telephone_text_field.getText());
     //   this.person.set_gender(gender_text_field.getText());
        this.person.set_birth_date(Date.valueOf(birth_date_date_picker.getValue()));

        String temp_gender = this.gender_combo_box.getSelectionModel().getSelectedItem().toString();

        if(temp_gender.equals("Masculino"))
            this.person.set_gender("M");
        else if (temp_gender.equals("Femenino"))
            this.person.set_gender(("F"));

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
           // this.gender_text_field.setDisable(true);
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
