package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primary_stage;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/login_ui.fxml"));
        primaryStage.setTitle("Iniciar sesion | Galeno (C) 2016");
        primaryStage.setScene(new Scene(root, 630 , 640));
        primaryStage.show();

        primary_stage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
