package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import db_helper.Db_connection;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class main_io_controller implements Initializable
{
    @FXML private TextField user_text_field;
    @FXML private TextField pass_text_field;
    @FXML private Db_connection db = new Db_connection();
    //@FXML private Connection con;
    @FXML private boolean login;
    @FXML private int rol;

    @FXML private Pane pane;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // todo temp setting to expedite login process.
        //this.user_text_field.setText("jose");  //doctor
        this.user_text_field.setText("adm");   //admin
       // this.user_text_field.setText("nom");        //reception
        this.pass_text_field.setText("1234");
    }


    @FXML
    protected void handle_login_button_action(ActionEvent event)
    { //throws SQLException {
        try
        {
            login = false;
            System.out.println("clicked Entrar");
            //con = db.get_connection();
            //Statement st = con.createStatement();
            //ResultSet rs = st.executeQuery("select name,pass,rol from Person p, User u where p.id=u.id");
            ResultSet rs = db.execute_query("select name, pass, rol, id from Person p, User u where p.id=u.person_id");
            //    ResultSet ps = st.executeQuery("select pass from User");


            while (rs.next())
            {
                String name = rs.getString("name");
                String pass = rs.getString("pass");
                Long user_id = rs.getLong("id");

                System.out.println("name: " + name);
                System.out.println("field: " + user_text_field.getText());
                System.out.println("id: " + user_id.toString());


                if (user_text_field.getText().equals(name) && pass_text_field.getText().equals(pass))
                {
                    System.out.println("User name registered");
                    login = true;
                    rol = rs.getInt("rol");



                    //Stage stage = (Stage) user_text_field.getScene().getWindow();
                    //stage.close();
                    //Parent root = null;


                    if (rol == 1)
                    {
                        try {

                            //root = FXMLLoader.load(getClass().getResource("/ui/reception_ui.fxml"));
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/reception_ui.fxml"));

                            Parent root = (Parent)fxmlLoader.load();
                            Reception_controller controller = fxmlLoader.<Reception_controller>getController();
                            controller.set_user_id(user_id);

                            //Scene scene = new Scene(root);
                            pane.getChildren().setAll(root);

                            //Stage stage = (Stage) root.getScene().getWindow();
                            //stage.setScene(scene);
                            //stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("before stage");
                        //stage.setScene(new Scene(root, 600, 400));
                        //Scene scene =  new Scene(root);
                        //stage.setScene(scene);
                        //stage.show();
                    }

                    else if (rol == 2)
                    {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/doctor_ui.fxml"));

                            Parent root = (Parent)fxmlLoader.load();
                            Doctor_controller controller = fxmlLoader.<Doctor_controller>getController();
                            controller.set_user_id(user_id);
                            controller.initialize(null, null);
//
//                            Timer timer = new Timer();
//                            TimerTask task = new TimerTask()
//                            {
//                                @Override
//                                public void run()
//                                {
//                                    controller.handle_search_button_action(new ActionEvent());
//                                    //System.out.println("TIMEEEER");
//                                }
//                            };
//
//                            timer.schedule(task,0,1000);

                            Timeline time = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>()
                            {
                                @Override
                                public void handle(ActionEvent event)
                                {
                                    controller.test();
                                }
                            }));

                            time.setCycleCount(Timeline.INDEFINITE);
                            time.play();

                            pane.getChildren().setAll(root);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (rol == 3)
                    {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/admin_ui.fxml"));

                            Parent root = (Parent)fxmlLoader.load();
                            Admin_controller controller = fxmlLoader.<Admin_controller>getController();
                            controller.set_user_id(user_id);
                            //controller.initialize(null, null);

                            pane.getChildren().setAll(root);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
