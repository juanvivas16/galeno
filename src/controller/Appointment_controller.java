package controller;

import data_model.Appointment;
import data_model.Appointment_type;
import data_model.Person;
import db_helper.Db_connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by victory on 4/18/16.
 */
public class Appointment_controller implements Initializable
{
    @FXML private Label status_label;
    @FXML private ComboBox<Person> doctor_combo_box;
    @FXML private ComboBox type_combo_box;
    @FXML private DatePicker date_date_picker;
    @FXML private TextField time_text_field;
    @FXML private TextArea description_text_area;

    private Long user_id, patient_id, doctor_id;
    private Db_connection db = new Db_connection();



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        //populate doctors combo box from DB
        String q = "SELECT name, last_name, id FROM Person p, User u WHERE u.rol = 2 AND u.person_id = p.id";
        ResultSet rs = db.execute_query(q);
        List<Person> person_list = new ArrayList<Person>();

        try
        {
            while (rs.next())
            {
                Person p = new Person();
                p.set_id(rs.getLong("id"));
                p.set_name(rs.getString("name"));
                p.set_last_name(rs.getString("last_name"));

                person_list.add(p);
            }

            ObservableList<Person> observable_person_list = FXCollections.observableArrayList(person_list);
            this.doctor_combo_box.getItems().clear();
            this.doctor_combo_box.setItems(observable_person_list);
            this.doctor_combo_box.getSelectionModel().selectFirst();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        //populate type combo box
        ObservableList<String> type_list =
                FXCollections.observableArrayList(
                  "Control",
                  "Primera Cita",
                  "Vacunacion",
                  "Enfermedad",
                  "Viaje"
                );

        this.type_combo_box.getItems().clear();
        this.type_combo_box.setItems(type_list);
        this.type_combo_box.getSelectionModel().selectFirst();


        //set date to today
        this.date_date_picker.setValue(LocalDate.now());

    }


    @FXML protected void handle_add_appointment_button_action(ActionEvent event)
    {
        Appointment appointment = new Appointment();
        appointment.set_user_id(this.get_user_id());
        appointment.set_patient_id(this.get_patient_id());

        //get_medic_id  combo box
        appointment.set_doctor_id(this.doctor_combo_box.getSelectionModel().getSelectedItem().get_id());


        appointment.set_date(Date.valueOf(this.date_date_picker.getValue()));
        appointment.set_time(Time.valueOf(this.time_text_field.getText()));
        appointment.set_description(this.description_text_area.getText());

        //get_appointment_type  combo box
        String temp_type = this.type_combo_box.getSelectionModel().getSelectedItem().toString();
        //parse appointment type
        Appointment_type temp_apoint_type = Appointment_type.ROUTINE_CHECKUP;

        if (temp_type.equals("Control"))
            temp_apoint_type = Appointment_type.ROUTINE_CHECKUP;
        else if (temp_type.equals("Primera Cita"))
            temp_apoint_type = Appointment_type.FIRST_APPOINTMENT;
        else if (temp_type.equals("Vacunacion"))
            temp_apoint_type = Appointment_type.VACCINATIONS;
        else if (temp_type.equals("Enfermedad"))
            temp_apoint_type = Appointment_type.SICK_VISIT;
        else if (temp_type.equals("Viaje"))
            temp_apoint_type = Appointment_type.TRAVEL_CLINIC;

        appointment.set_type(temp_apoint_type);

        //add user_id and patient_id to the appointment
        appointment.set_patient_id(this.patient_id);
        appointment.set_user_id(this.user_id);

        //insert into the DB

        if (this.insert_appointment_in_db(appointment) != 0)
            this.status_label.setText("¡Cita Agregada con éxito!");

            //todo go back to reception of the same patient

        else
            this.status_label.setText("Error al insertar");

    }


    private int insert_appointment_in_db(Appointment a)
    {
        String query = "INSERT INTO Appointment VALUES (" + a.get_id() + "" +
                ", '" + a.get_patient_id() + "'" +
                ", '" + a.get_user_id() + "'" +
                ", '" + a.get_doctor_id() + "'" +
                ", '" + a.get_date() + "'" +
                ", '" + a.get_time() + "'" +
                ", '" + a.get_description() + "'" +
                ", '" + a.get_type() + "'" +
                ")";

        return db.execute_update(query);
    }


    @FXML protected void handle_cancel_button_action(ActionEvent event)
    {

    }

    public Long get_user_id()
    {
        return user_id;
    }

    public void set_user_id(Long user_id)
    {
        this.user_id = user_id;
    }

    public Long get_patient_id()
    {
        return patient_id;
    }

    public void set_patient_id(Long patient_id)
    {
        this.patient_id = patient_id;
    }

    public Long get_doctor_id()
    {
        return doctor_id;
    }

    public void set_doctor_id(Long doctor_id)
    {
        this.doctor_id = doctor_id;
    }


}
