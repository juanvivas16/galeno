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
    @FXML private Tab prescription_tab;
    @FXML private Tab consultation_tab;
    @FXML private TabPane total_pane;
    @FXML private TableView person_table;
    @FXML private TableView appointment_table;
    @FXML private TableView invoice_table;
    @FXML private TableView prescription_table;
    @FXML private TableView consultation_table;
    @FXML private ObservableList<ObservableList> person_data;
    @FXML private ObservableList<ObservableList> appointment_data;
    @FXML private ObservableList<ObservableList> invoice_data;
    @FXML private ObservableList<ObservableList> prescription_data;
    @FXML private ObservableList<ObservableList> consultation_data;
    @FXML private Db_connection db = new Db_connection();


    private Long user_id = new Long(0);



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //se debe inicializar la primera tabla
        buildAppointmentData();
        buildConsultationData();
        buildInvoiceData();
        buildPersonData();
        buildPrescriptionData();

    }


    public void buildPersonData()
    {
        person_data = FXCollections.observableArrayList();

        try{
            String qu_p = "SELECT * from Person";
            ResultSet rs = db.execute_query(qu_p);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
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

        try{
            String qu_a = "SELECT * from Appointment";
            ResultSet rs = db.execute_query(qu_a);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
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

        try{
            String qu_i = "SELECT * from Invoice";
            ResultSet rs = db.execute_query(qu_i);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
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

    public void buildPrescriptionData()
    {
        prescription_data = FXCollections.observableArrayList();

        try{
            String qu_pr = "SELECT * from Prescription";
            ResultSet rs = db.execute_query(qu_pr);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
                {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
                    {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                prescription_table.getColumns().addAll(col);
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
                prescription_data.add(row);
            }

            //FINALLY ADDED TO TableView
            prescription_table.setItems(prescription_data);

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void buildConsultationData()
    {
        consultation_data = FXCollections.observableArrayList();

        try{
            String qu_c = "SELECT * from Consultation";
            ResultSet rs = db.execute_query(qu_c);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
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


    public Long get_user_id()
    {
        return user_id;
    }

    public void set_user_id(Long user_id)
    {
        this.user_id = user_id;
    }


}
