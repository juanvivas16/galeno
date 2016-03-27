package main_io;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller
{
    @FXML private TextField user_text_field;
    @FXML private TextField pass_text_field;

    @FXML protected void handle_login_button_action(ActionEvent event)
    {
        System.out.println("clicked Entrar");
        //user_text_field.getText()
        //pass_text_field.getText()


    }
}
