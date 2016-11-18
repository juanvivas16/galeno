package controller;

/**
 * Created by juancho on 24/04/16.
 */
import data_model.Appointment;
import data_model.Appointment_type;
import data_model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import db_helper.Db_connection;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Admin_controller implements Initializable
{
    @FXML private Button edit_data_button;
    @FXML private Button delete_data_button;
    @FXML private Button exit_button;
    @FXML private Tab person_tab;
    @FXML private Tab appointment_tab;
    @FXML private Tab invoice_tab;
    @FXML private Tab consultation_tab;
    @FXML private Tab users_tab;
    @FXML private TabPane total_pane;
    @FXML private TableView person_table;
    @FXML private TableView appointment_table;
    @FXML private TableView invoice_table;
    @FXML private TableView consultation_table;
    @FXML private TableView users_table;
    @FXML private ObservableList<ObservableList> person_data;
    @FXML private ObservableList<ObservableList> appointment_data;
    @FXML private ObservableList<ObservableList> invoice_data;
    @FXML private ObservableList<ObservableList> consultation_data;
    @FXML private ObservableList<ObservableList> users_data;

    @FXML private Db_connection db = new Db_connection();


    private Long user_id = new Long(0);



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //se debe inicializar la primera tabla y luego al darle click a cada tab inicializar el que corresponda

        buildAppointmentData();
        buildConsultationData();
        buildInvoiceData();
        buildPersonData();
        buildUsersData();
    }

    @FXML protected void handle_edit_button_action(ActionEvent event) throws IOException, SQLException
    {



    }

    @FXML protected void handle_remove_button_action(ActionEvent event)
    {

    }

    @FXML protected void handle_exit_button_action(ActionEvent event)
    {

    }

    private int delete_person_in_db(Person a)
    {
        String query = "DELETE FROM Appointment WHERE id = "+ a.get_id();

        return db.execute_update(query);
    }





    public void buildPersonData()
    {
        person_data = FXCollections.observableArrayList();
        String qu_p = "select name, last_name, phone_num, gender, direction, birth_date, reg_date from Person";
        ResultSet rs = db.execute_query(qu_p);

        try{

            List<String> lis = new ArrayList<String>();
            lis.add("Nombre");
            lis.add("Apellido");
            lis.add("Telefono");
            lis.add("Sexo");
            lis.add("Direccion");
            lis.add("FechaN.");
            lis.add("FechaR.");


            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(lis.get(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                person_table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }


//
//            person_table.getColumns().addAll(new TableColumn("Nombre"));
//            person_table.getColumns().addAll(new TableColumn("Apellido"));
//            person_table.getColumns().addAll(new TableColumn("Telefono"));
//            person_table.getColumns().addAll(new TableColumn("Sexo"));
//            person_table.getColumns().addAll(new TableColumn("Direccion"));
//            person_table.getColumns().addAll(new TableColumn("FechaN."));
//            person_table.getColumns().addAll(new TableColumn("FechaReg."));


            List<Person> list_person = new ArrayList<>();

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1; i <= 7; i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));

                }
                //String qu_p = "select name, last_name, phone_num, gender, direction, birth_date, reg_date from Person";
                //Person(Long id, String name, String last_name, String gender, Date birth_date, Date reg_date, String direction, String phone_num)

                Person p = new Person(new Long(0), rs.getString(1), rs.getString(2), rs.getString(4), Date.valueOf(rs.getString(6)), Date.valueOf(rs.getString(7)), rs.getString(5), rs.getString(3));
                list_person.add(p);

                System.out.println("Row [1] added "+row );
                person_data.add(row);
            }

            //FINALLY ADDED TO TableView
            person_table.setItems(person_data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void buildAppointmentData()
    {
        appointment_data = FXCollections.observableArrayList();

        String qu_a = "select p.name as Paciente, pp.name as Usuario, ppp.name as Doctor, a.description as Descripcion, a.type as Tipo, a.date as Fecha, a.time as Hora " +
                "FROM Appointment a J" +
                "OIN Person p ON p.id = a.patient_id " +
                "JOIN Person pp ON pp.id = a.user_id " +
                "JOIN Person ppp ON ppp.id=a.doctor_id";

        ResultSet rs = db.execute_query(qu_a);

        try{


            List<String> lis = new ArrayList<>();
            lis.add("Paciente");
            lis.add("Usuario");
            lis.add("Doctor");
            lis.add("Descripcion");
            lis.add("Tipo");
            lis.add("Fecha.");
            lis.add("Hora");

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                TableColumn col = new TableColumn(lis.get(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                appointment_table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                System.out.println("Row [1] added "+row );
                appointment_data.add(row);
            }

            //FINALLY ADDED TO TableView
            appointment_table.setItems(appointment_data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void buildInvoiceData()
    {
        invoice_data = FXCollections.observableArrayList();

        String qu_i = "SELECT i.consultation_id, p.name as Usuario, i.description, i.sub_total, i.iva, i.total " +
                "FROM Invoice i JOIN Person p ON p.id=i.user_id " +
                "JOIN Consultation c ON c.id=i.consultation_id";
        ResultSet rs = db.execute_query(qu_i);

        try{

            List<String> lis = new ArrayList<>();
            lis.add("ID-Consulta");
            lis.add("Usuario");
            lis.add("Descripcion");
            lis.add("Sub-Total");
            lis.add("IVA");
            lis.add("Total");

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                TableColumn col = new TableColumn(lis.get(i));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                invoice_table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                System.out.println("Row [1] added "+row );
                invoice_data.add(row);
            }

            //FINALLY ADDED TO TableView
            invoice_table.setItems(invoice_data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


    public void buildConsultationData()
    {
        consultation_data = FXCollections.observableArrayList();

        try{
            String qu_c = "SELECT p.name as Usuario, pp.name as Paciente, c.appointment_id as IDCita, c.paid as Pago " +
                    "FROM Consultation c JOIN Person p ON c.user_id=p.id " +
                    "JOIN Person pp ON c.patient_id = pp.id";
            ResultSet rs = db.execute_query(qu_c);

            List<String> lis = new ArrayList<>();

            lis.add("Usuario");
            lis.add("Paciente");
            lis.add("ID-Cita");
            lis.add("Pago");

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;

                //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                TableColumn col = new TableColumn(lis.get(i));

                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                consultation_table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                System.out.println("Row [1] added "+row );
                consultation_data.add(row);
            }

            //FINALLY ADDED TO TableView
            consultation_table.setItems(consultation_data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void buildUsersData()
    {
        users_data = FXCollections.observableArrayList();

        try{
            String qu_u = "SELECT p.name, p.last_name, p.phone_num, u.rol, u.pass, u.person_id  " +
                    "FROM User u JOIN Person p ON u.person_id = p.id";
            ResultSet rs = db.execute_query(qu_u);

            List<String> lis = new ArrayList<>();

            lis.add("Nombre");
            lis.add("Apellido");
            lis.add("Telefono");
            lis.add("Rol");
            lis.add("Clave");
            lis.add("ID-Persona");

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                //TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                TableColumn col = new TableColumn(lis.get(i));

                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                users_table.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next())
            {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();

                for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                System.out.println("Row [1] added "+row );
                users_data.add(row);
            }

            //FINALLY ADDED TO TableView
            users_table.setItems(users_data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }



    public Long get_user_id()
    {
        return user_id;
    }

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
