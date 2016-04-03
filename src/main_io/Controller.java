package main_io;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import db_helper.Db_connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
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
    protected boolean handle_login_button_action(ActionEvent event){ //throws SQLException {
        try {
            login = false;
            System.out.println("clicked Entrar");
            con = db.get_connection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select name,pass from Person p, User u where p.id=u.id");
            //    ResultSet ps = st.executeQuery("select pass from User");

            while (rs.next()) {
                String name = rs.getString("name");
                String pass = rs.getString("pass");
                System.out.println("name: " + name);
                System.out.println("field: " + user_text_field.getText());

                if (user_text_field.getText().equals(name) && pass_text_field.getText().equals(pass)) {
                    System.out.println("User name registered");
                    login = true;
                }

            }
        } catch (SQLException err) {
            System.out.println(err);
        }
        return login;
        //user_text_field.getText()
        //pass_text_field.getText()
    }
}
