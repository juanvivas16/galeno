package controller;

import data_model.Record;
import data_model.Test_item;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by victory on 4/22/16.
 */
public class Test_controller implements Initializable
{

    @FXML private TextArea test_text_area;
    @FXML private ListView test_list_view;
    @FXML private Label status_label;
    @FXML private Pane pane;


    @FXML private Db_connection db = new Db_connection();


    private Long user_id;   //medic_id
    private Long patient_id;
    private Long consultation_id;
    private Long appointment_id;
    private Long test_id;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }


    protected  void populate_test_list_view(ActionEvent event)
    {
        //populate appointment list view

        String qu = "SELECT id, test_id, description " +
                    "FROM Test_item " +
                    "WHERE test_id = '" + this.get_test_id().toString() + "' ";

        ResultSet rss = db.execute_query(qu);
        List<Test_item> test_list = new ArrayList<Test_item>();

        try
        {
            if (rss != null)
            {
                while (rss.next())
                {
                    Test_item t = new Test_item();
                    t.set_id(rss.getLong("id"));
                    t.set_test_id(rss.getLong("test_id"));
                    t.set_description(rss.getString("description"));

                    test_list.add(t);
                }
                ObservableList<Test_item> observable_appointment_list = FXCollections.observableArrayList(test_list);
                this.test_list_view.getItems().clear();
                this.test_list_view.setItems(observable_appointment_list);
                this.test_list_view.getSelectionModel().selectLast();
                this.test_list_view.scrollTo(this.test_list_view.getSelectionModel().getSelectedItem());

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



    @FXML protected void handle_add_test_button_action(ActionEvent event) throws SQLException
    {
        //insert test_item into the DB and repopulate the list of tests
        if (this.test_text_area.getText().isEmpty())
            return;
        this.insert_test_in_db();
        this.populate_test_list_view(null);
        this.test_text_area.clear();


    }


    @FXML protected void handle_done_button_action(ActionEvent event) throws IOException
    {
        //send back the consultation_id and the test_id and go back to consultation ui
        // initiate consultation ui and send patient and user (medic) id
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/consultation_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        //access another controller to pass parameters and populate list view
        Consultation_controller controller = fxmlLoader.<Consultation_controller>getController();
        controller.set_consultation_id(this.consultation_id);
        controller.set_test_id(this.test_id);
        controller.set_user_id(this.user_id);
        controller.set_patient_id(this.patient_id);
        controller.set_appointment_id(this.appointment_id);

        controller.populate_records_list_view(null);

        pane.getChildren().setAll(root);
    }

    private int insert_test_in_db() throws SQLException
    {
        //insert a new test_item into the db
        String query = "INSERT INTO Test_item ( test_id, description  ) " +
                "VALUES ( '" + this.test_id.toString() + "'"+
                ", '" + this.test_text_area.getText()  + "' )";

        return db.execute_update(query);
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
}
