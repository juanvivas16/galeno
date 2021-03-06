package controller;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import data_model.Appointment;
import data_model.Appointment_type;
import db_helper.Db_connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.sql.Date;
import java.util.*;



/**
 * Created by victory on 4/21/16.
 * Clase del controlador para la interfaz del médico
 */
public class Doctor_controller implements Initializable
{
    @FXML private TextField id_text_field;
    @FXML private ListView next_appointment_list_view;
    @FXML private Label status_label;
    @FXML private Pane pane;
    @FXML private Button process_button;
    @FXML private Label user_name_label;
    @FXML private MenuItem menuItem_exit;


    @FXML private Db_connection db = new Db_connection();

    private Long user_id = new Long(0);
    private Long patient_id = new Long(0);
    private Long consultaion_id = new Long(0);
    private Long appointment_id;


    /**
     * Inicializador del controlador de la interfaz
     * poblar las listas con las citas proximas (de hoy)
     * @param location ubicacion
     * @param resources recursos
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.process_button.setDisable(true);

        //populate appointment list view
        java.util.Date date = new java.util.Date();

        String qu = "SELECT a.id, a.patient_id, a.doctor_id, a.description, a.type, a.date, a.time, p.name," +
                " p.last_name FROM Appointment a, Person p " +
                "WHERE a.doctor_id = p.id AND a.doctor_id = '" + this.user_id.toString() + "' AND " +
                "a.date = ' " + new Date(date.getTime()).toString() + " ' AND a.done = '0' ";


        //System.out.print(new Date(date.getTime()).toString());
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


                //if the list view is not empty, at least one element selected, enable process button
                if (! this.next_appointment_list_view.getItems().isEmpty())
                {
                    this.process_button.setDisable(false);
                }

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

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

    }

    /**
     * Procesar la lógica del examen
     * desactivar el boton de procesar
     * preparar la consulta para obtener los examenes relacionados al paciente con user_id
     */
    public void test()
    {
        System.out.print("*");
        this.process_button.setDisable(true);

        //populate appointment list view
        java.util.Date date = new java.util.Date();

        String qu = "SELECT a.id, a.patient_id, a.doctor_id, a.description, a.type, a.date, a.time, p.name," +
                " p.last_name FROM Appointment a, Person p " +
                "WHERE a.doctor_id = p.id AND a.doctor_id = '" + this.user_id.toString() + "' AND " +
                "a.date = ' " + new Date(date.getTime()).toString() + " ' AND a.done = '0' ";


        //System.out.print(new Date(date.getTime()).toString());
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


                //if the list view is not empty, at least one element selected, enable process button
                if ( ! observable_appointment_list.isEmpty())
                {
                    this.process_button.setDisable(false);
                }

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @FXML protected void handle_id_text_changed_action(ActionEvent event)
    {
        //todo search as start typing

    }

    /**
     * Procesar la lógica del botón Buscar
     * si no hay texto, se muestran todas las citas de día actual (hoy)
     * de lo contrario se buscara la/s cita/s del paciente con el id dado
     * @param event
     */
    @FXML protected void handle_search_button_action(ActionEvent event)
    {
        if (this.id_text_field.getText().isEmpty())
        {
            this.initialize(null, null);
            return;
        }

        this.process_button.setDisable(true);

        //filter list on id search value

        //populate appointment list view
        java.util.Date date = new java.util.Date();

        String qu = "SELECT a.id, a.patient_id, a.doctor_id, a.description, a.type, a.date, a.time, p.name," +
                " p.last_name FROM Appointment a, Person p " +
                "WHERE a.doctor_id = p.id " + " AND a.doctor_id = '" + this.user_id.toString() + "' " +
                "AND a.patient_id = '" + this.id_text_field.getText() + "' AND " +
                "a.date = ' " + new Date(date.getTime()).toString() + " ' AND a.done = '0' ";

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

                //if the list view is not empty, at least one element selected, enable process button
                if (! this.next_appointment_list_view.getItems().isEmpty())
                {
                    this.process_button.setDisable(false);
                }


            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    /**
     * Procesar la lógica del boton Procesar
     * Preparar el modelo de una consulta, si la consulta aun esta abierto, procesar la misam
     * de lo contario crear una nueva consulta.
     * Cambiar a la interfaz de consulta, y pasar los datos necesarios al controlador de consulta
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML protected void handle_process_button_action(ActionEvent event) throws IOException, SQLException
    {
        //verify and get selected row from the list of appointments
        if (! this.next_appointment_list_view.getItems().isEmpty())
        {
            Appointment ap = (Appointment) this.next_appointment_list_view.getSelectionModel().getSelectedItem();
            this.patient_id = ap.get_patient_id();
            this.appointment_id = ap.get_id();

            String query = "SELECT id FROM Consultation WHERE appointment_id = '" +
                    ap.get_id().toString() + "' ";
            ResultSet rs = db.execute_query(query);


            //value exist
            if (rs.next())
            {
                this.consultaion_id = rs.getLong("id");
            }
            else
            {
                //insert a new test in db, and retrieve the autoincrement Test ID
                int ret = this.insert_consultation_in_db();
                if (ret != -1)
                    this.consultaion_id = new Long(ret);
            }

/*
            //insert new consultation row, and send its generated id to consultation ui controller
            int res = this.insert_consultation_in_db();
            if (res != -1)
                this.consultaion_id = new Long(res);
*/

            // initiate consultation ui and send patient and user (medic) id
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/consultation_ui.fxml"));

            Parent root = (Parent)fxmlLoader.load();
            //access another controller to pass parameters and populate list view
            Consultation_controller controller = fxmlLoader.<Consultation_controller>getController();
            controller.set_user_id(this.user_id);
            controller.set_patient_id(this.patient_id);
            controller.set_consultation_id(this.consultaion_id);
            controller.set_appointment_id(ap.get_id());
            controller.populate_records_list_view(null);

            Main.primary_stage.setTitle("Consulta | Galeno (C) 2016");

            pane.getChildren().setAll(root);


        }
    }

    /**
     * Insertar una consulta en la base de datos
     * prepara la consulta y luego ejecutarla
     * @return int estado d ela inserción
     * @throws SQLException
     */
    private int insert_consultation_in_db() throws SQLException
    {

        //get the last generated consultation ID, to send later to Consultation ui controller
        String query = "INSERT INTO Consultation ( user_id, patient_id, appointment_id) " +
                "VALUES (" + this.user_id + "" +
                ", '" + this.patient_id + "'" +
                ", '" + this.appointment_id + "'" +
                ")";

        java.sql.PreparedStatement prstat;
        Connection con = db.get_connection();
        prstat = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        int ret = prstat.executeUpdate();
        ResultSet rs = prstat.getGeneratedKeys();

        ret = -1;

        if (rs.next())
        {
            ret = rs.getInt(1);
        }

        return ret;

    }

    /**
     * Obtener el id del usuario
     * @return Long id del usuario
     */
    public Long get_user_id()
    {
        return user_id;
    }

    /**
     * Asignar el id del usuario
     * @param user_id Long id de entrada
     */
    public void set_user_id(Long user_id)
    {
        this.user_id = user_id;
    }


    @FXML
    protected void handle_menu_item_exit_action(ActionEvent e)
    {
        System.exit(0);
    }


}
