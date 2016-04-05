package controller;

/**
 * Created by juancho on 04/04/16.
 */



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import db_helper.Db_connection;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class reception_controller
{
    @FXML
    private Db_connection db = new Db_connection();
    @FXML
    private Connection con;



    @FXML
    protected void handle_add_payment_button_action(ActionEvent event)
    {

    }

    @FXML
    protected void handle_edit_appointment_button_action(ActionEvent event)
    {

    }

    @FXML
    protected void handle_remove_appointment_button_action(ActionEvent event)
    {

    }

    @FXML
    protected void handle_add_appointment_button_action(ActionEvent event)
    {

    }

    @FXML
    protected void handle_add_queue_button_action(ActionEvent event)
    {

    }

    @FXML
    protected void handle_remove_queue_button_action(ActionEvent event)
    {

    }
}
