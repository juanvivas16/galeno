package controller;

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

public class main_io_controller {
    @FXML
    private TextField user_text_field;
    @FXML
    private TextField pass_text_field;
    @FXML
    private Db_connection db = new Db_connection();
    @FXML
    private Connection con;
    @FXML
    private boolean login;
    @FXML
    private int rol;



    @FXML
    protected void handle_login_button_action(ActionEvent event)
    { //throws SQLException {
        try
        {
            login = false;
            System.out.println("clicked Entrar");
            con = db.get_connection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select name,pass,rol from Person p, User u where p.id=u.id");
            //    ResultSet ps = st.executeQuery("select pass from User");


            while (rs.next())
            {
                String name = rs.getString("name");
                String pass = rs.getString("pass");
                System.out.println("name: " + name);
                System.out.println("field: " + user_text_field.getText());

                if (user_text_field.getText().equals(name) && pass_text_field.getText().equals(pass))
                {
                    System.out.println("User name registered");
                    login = true;
                    rol = rs.getInt("rol");



                    Stage stage = (Stage) user_text_field.getScene().getWindow();
                    stage.close();
                    Parent root = null;


                    if (rol == 1)
                    {
                        try {

                            root = FXMLLoader.load(getClass().getResource("/ui/reception_ui.fxml"));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("before stage");
                        //stage.setScene(new Scene(root, 600, 400));
                        Scene scene =  new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }

                    else if (rol == 2)
                    {
                        try {
                            root = FXMLLoader.load(getClass().getResource("/ui/doctor_ui.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.setScene(new Scene(root, 600, 400));
                    }

                }
            }
//    Stage stage = (Stage) user_text_field.getScene().getWindow();
        } catch (SQLException err) {
            System.out.println(err);
        }
        //user_text_field.getText()
        //pass_text_field.getText()
    }
}
