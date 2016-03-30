package db_helper;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by victory on 3/27/16.
 */
public class Db_connection
{
    private Connection _con = null;
    private String _url;
    private String _user;
    private String _pass;


    private boolean get_data_from_prop()
    {
        Properties __prop = new Properties();
        FileInputStream __in = null;
        try
        {
            __in = new FileInputStream("src/etc/db_config.properties");
            //System.out.println("properties readed");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            //System.out.println("properties faild");
            return false;
        }

        try
        {
            __prop.load(__in);
            this._url  = __prop.getProperty("url");
            this._user = __prop.getProperty("user");
            this._pass = __prop.getProperty("pass");

            __in.close();

            //System.out.println("properties loaded"+ _url + _user + _pass);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }



    private boolean init_connection()
    {
        if (get_data_from_prop())
        {
            try
            {
                _con = DriverManager.getConnection(this._url, this._user, this._pass);

                //System.out.println("connected to db");


            } catch (SQLException e)
            {
                //System.out.println("Faild to connect to db");

                e.printStackTrace();
                return false;
            }
            return true;
        }
        else
            return false;
    }



    public Connection get_connection()
    {
        if (init_connection())
        {
            return this._con;
        }
        else
            return null;
    }


    public boolean close_connection()
    {
        if (this._con != null)
        {
            try
            {
                _con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return true;
    }

}
