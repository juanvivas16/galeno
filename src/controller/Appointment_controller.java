package controller;

import com.sun.org.apache.xpath.internal.operations.Or;
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
    @FXML private ListView appointment_list_view;

    private Long user_id, patient_id, doctor_id;
    private Db_connection db = new Db_connection();

    private boolean edit_mode = false;


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

        /*

        */

    }


    public void populate_appointment_list_view()
    {
        //populate appointment list view
        String qu = "SELECT a.id, a.patient_id, a.doctor_id, a.description, a.type, a.date, a.time, p.name," +
                " p.last_name FROM Appointment a, Person p " +
                "WHERE a.doctor_id = p.id AND a.patient_id = " + this.get_patient_id().toString();
        ResultSet rss = db.execute_query(qu);
        List<Appointment> appointments_list = new ArrayList<Appointment>();

        try
        {
            while(rss.next())
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
            this.appointment_list_view.getItems().clear();
            this.appointment_list_view.setItems(observable_appointment_list);
            this.appointment_list_view.getSelectionModel().selectFirst();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @FXML protected void handle_edit_appointment_button_action(ActionEvent event) throws SQLException
    {
        //if the list view is not empty, at least one element selected, populate forms
        if (! this.appointment_list_view.getItems().isEmpty())
        {
            Appointment ap = (Appointment) this.appointment_list_view.getSelectionModel().getSelectedItem();
            //System.out.print(ap);
            this.doctor_combo_box.getSelectionModel().select(ap.get_doctor());
            this.date_date_picker.setValue(LocalDate.parse(ap.get_date().toString()));
            this.time_text_field.setText(ap.get_time().toString());
            //this.type_combo_box.getSelectionModel().
            this.description_text_area.setText(ap.get_description());

            this.doctor_id = ap.get_doctor_id();
            this.patient_id = ap.get_patient_id();
            //this.user_id = ap.get_user_id();

            this.edit_mode = true;

        }
    }


    @FXML protected void handle_remove_appointment_button_action(ActionEvent event)
    {

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


        if (! this.edit_mode)
        {
            //insert into the DB
            if (this.insert_appointment_in_db(appointment) != 0)
                this.status_label.setText("¡Cita Agregada con éxito!");

                //todo go back to reception of the same patient

            else
                this.status_label.setText("Error al insertar");
        }
        else
        {
            //todo update appointment data in db
        }
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
