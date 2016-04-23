package controller;

import com.mysql.jdbc.Statement;
import data_model.Appointment;
import data_model.Appointment_type;
import data_model.Record;
import db_helper.Db_connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by victory on 4/21/16.
 */
public class Consultation_controller implements Initializable
{

    @FXML private TextArea record_text_area;
    @FXML private ListView records_list_view;
    @FXML private Label status_label;
    @FXML private Pane pane;

    private Long user_id;   //medic_id
    private Long patient_id;
    private Long consultation_id;
    private Long appointment_id;
    private Long test_id;
    private Long prescription_id;


    @FXML private Db_connection db = new Db_connection();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    protected  void populate_records_list_view(ActionEvent event)
    {
        //populate appointment list view
        java.util.Date date = new java.util.Date();

        String qu = "SELECT visit_date, description " +
                "FROM Record " +
                "WHERE patient_id = '" + this.patient_id.toString() + "' " +
                "ORDER BY visit_date DESC" ;

        //System.out.print(new Date(date.getTime()).toString());
        ResultSet rss = db.execute_query(qu);
        List<Record> records_list = new ArrayList<Record>();

        try
        {
            if (rss != null)
            {
                while (rss.next())
                {
                    Record a = new Record();
                    a.set_visit_date(rss.getDate("visit_date"));
                    a.set_description(rss.getString("description"));
                    records_list.add(a);
                }
                ObservableList<Record> observable_appointment_list = FXCollections.observableArrayList(records_list);
                this.records_list_view.getItems().clear();
                this.records_list_view.setItems(observable_appointment_list);
                this.records_list_view.getSelectionModel().selectLast();
                this.records_list_view.scrollTo(this.records_list_view.getSelectionModel().getSelectedItem());

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    @FXML protected void handle_add_record_button_action(ActionEvent event)
    {
        if (this.record_text_area.getText().isEmpty())
            return;

        Record record = new Record();
        record.set_patient_id(this.patient_id);
        record.set_user_id(this.user_id);
        record.set_consultation_id(this.consultation_id);
        record.set_description(this.record_text_area.getText());

        String query = "INSERT INTO Record ( patient_id, consultation_id, user_id, visit_date, description) " +
                "VALUES (" + record.get_patient_id() + "" +
                ", '" + record.get_consultation_id() + "'" +
                ", '" + record.get_user_id() + "'" +
                ", '" + record.get_visit_date() + "'" +
                ", '" + record.get_description() + "'" +
                ")";

        int ret = db.execute_update(query);

        this.populate_records_list_view(null);
        this.record_text_area.clear();
    }

    @FXML protected void handle_test_button_action(ActionEvent event) throws SQLException, IOException
    {
        //go to test (exam) ui controller and pass consultation_id and new generated test_id
        //verify if there are no test with the consultation_id, then insert new test, else show it


        String query = "SELECT id, consultation_id FROM Test WHERE consultation_id = '" +
                        this.consultation_id.toString() + "' ";
        ResultSet rs = db.execute_query(query);
        //value exist
        if (rs.next())
        {
            this.test_id = rs.getLong("id");
        }
        else
        {
            //insert a new test in db, and retrieve the autoincrement Test ID
            int ret = this.insert_test_in_db();
            if (ret != -1)
                this.test_id = new Long(ret);
        }

        //go to TEST UI

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/test_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        Test_controller controller = fxmlLoader.<Test_controller>getController();
        controller.set_consultation_id(this.consultation_id);
        controller.set_test_id(this.test_id);
        controller.set_user_id(this.user_id);
        controller.set_patient_id(this.patient_id);
        controller.set_appointment_id(this.appointment_id);

        controller.populate_test_list_view(null);

        pane.getChildren().setAll(root);


    }




    @FXML protected void handle_prescription_button_action(ActionEvent event) throws SQLException, IOException
    {
        //todo go to prescription ui controller and pass consultation_id
        //verify if there are no test with the prescription_id, then insert new prescription, else show it


        String query = "SELECT id, consultation_id FROM Prescription WHERE consultation_id = '" +
                this.consultation_id.toString() + "' ";
        ResultSet rs = db.execute_query(query);
        //value exist
        if (rs.next())
        {
            this.prescription_id = rs.getLong("id");
        }
        else
        {
            //insert a new test in db, and retrieve the autoincrement Test ID
            int ret = this.insert_prescription_item_in_db();
            if (ret != -1)
                this.prescription_id = new Long(ret);
        }

        //go to TEST UI

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/prescription_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        Prescription_controller controller = fxmlLoader.<Prescription_controller>getController();
        controller.set_consultation_id(this.consultation_id);
        controller.set_prescription_id(this.prescription_id);
        controller.set_user_id(this.user_id);
        controller.set_patient_id(this.patient_id);
        controller.set_appointment_id(this.appointment_id);

        controller.populate_test_list_view(null);

        pane.getChildren().setAll(root);


    }




    @FXML protected void handle_done_button_action(ActionEvent event)
    {
        //go back to Doctor ui controller, update appointment to done


        String query = "UPDATE Appointment SET done = '1' " +
                "WHERE id = '" + this.appointment_id.toString() + "'";

        db.execute_update(query);


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/doctor_ui.fxml"));

            Parent root = (Parent)fxmlLoader.load();
            Doctor_controller controller = fxmlLoader.<Doctor_controller>getController();
            controller.set_user_id(user_id);
            controller.initialize(null, null);

            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private int insert_test_in_db() throws SQLException
    {

        //get the last generated TEST ID (from autoincrement), to send later to Test ui controller
        String query = "INSERT INTO Test ( consultation_id ) " +
                "VALUES ( '" + this.consultation_id.toString() + "' )";

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


    private int insert_prescription_item_in_db() throws SQLException
    {

        //get the last generated TEST ID (from autoincrement), to send later to Test ui controller
        String query = "INSERT INTO Prescription ( consultation_id ) " +
                "VALUES ( '" + this.consultation_id.toString() + "' )";

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


    public Long get_consultation_id()
    {
        return consultation_id;
    }

    public void set_consultation_id(Long consultation_id)
    {
        this.consultation_id = consultation_id;
    }

    public Long get_appointment_id()
    {
        return appointment_id;
    }

    public void set_appointment_id(Long appointment_id)
    {
        this.appointment_id = appointment_id;
    }


    public Long get_test_id()
    {
        return test_id;
    }

    public void set_test_id(Long test_id)
    {
        this.test_id = test_id;
    }

    public Long get_prescription_id()
    {
        return prescription_id;
    }

    public void set_prescription_id(Long prescription_id)
    {
        this.prescription_id = prescription_id;
    }
}
