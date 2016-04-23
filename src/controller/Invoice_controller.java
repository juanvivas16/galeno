package controller;

import db_helper.Db_connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by victory on 4/22/16.
 */
public class Invoice_controller implements Initializable
{


    @FXML private TextField description_text_field;
    @FXML private TextField subtotoal_text_field;
    @FXML private TextField iva_text_field;
    @FXML private TextField total_text_field;
    @FXML private Pane pane;

    protected Long patient_id;
    protected Long appointment_id;
    protected Long user_id;

    protected Long consultation_id;


    @FXML private Db_connection db = new Db_connection();


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML protected void handle_done_button_action(ActionEvent event) throws IOException, SQLException
    {
        //mark consultation as paid and go back to the reception main UI
        String query = "UPDATE Consultation SET paid = 1 WHERE appointment_id = '" + this.appointment_id + "'";

        int ret = db.execute_update(query);

        query = "UPDATE Appointment SET done = 2 WHERE id = '" + this.appointment_id + "'";

        ret = db.execute_update(query);


        //get consultation_id
        query = "SELECT id FROM Consultation WHERE appointment_id = '" + this.appointment_id + "'";

        ResultSet rs = db.execute_query(query);

        if (rs.next())
        {
            this.consultation_id = rs.getLong("id");


            //todo insert invoice in db
            query = "INSERT INTO Invoice (consultation_id, user_id, description, sub_total, iva, total)" +
                    "VALUES ( '" + this.consultation_id + "', '" + this.user_id + "', '" +
                    this.description_text_field.getText() + "', '" + this.subtotoal_text_field.getText() +
                    "', '" + this.iva_text_field.getText() + "', '" + this.total_text_field.getText() + "' )";

            db.execute_update(query);

        }
        //go to reception UI

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/reception_ui.fxml"));

        Parent root = (Parent)fxmlLoader.load();
        //access another controller to pass parameters and populate list view
        Reception_controller controller = fxmlLoader.<Reception_controller>getController();
        controller.set_user_id(this.user_id);
        controller.id_text_field.setText(this.patient_id.toString());
        controller.handle_search_button_action(new ActionEvent());

        pane.getChildren().setAll(root);


    }


    @FXML protected void handle_subtotal_key_relase_action(KeyEvent event) throws IOException, SQLException
    {

        if (this.subtotoal_text_field.getText().isEmpty())
            return;

        float sub_total =  Float.parseFloat(this.subtotoal_text_field.getText());
        float iva =  Float.parseFloat(this.iva_text_field.getText());

        float total = sub_total + sub_total * ( iva / 100 );

        this.total_text_field.setText(Float.toString(total));

    }



    public Long get_patient_id()
    {
        return patient_id;
    }

    public void set_patient_id(Long patient_id)
    {
        this.patient_id = patient_id;
    }

    public Long get_appointment_id()
    {
        return appointment_id;
    }

    public void set_appointment_id(Long appointment_id)
    {
        this.appointment_id = appointment_id;
    }

    public Long get_user_id()
    {
        return user_id;
    }

    public void set_user_id(Long user_id)
    {
        this.user_id = user_id;
    }
}
