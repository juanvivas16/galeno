package data_model;

import db_helper.Db_connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;

/**
 * Created by victory on 3/26/16.
 */
public class Appointment
{
    private Long _id;
    private Long _patient_id;
    private Long _user_id;
    private Long _doctor_id;
    //private String _phone_num;
    private Date _date;
    private Time _time;
    private String _description;
    private Appointment_type _type;


    public Appointment(Long _id, Long _patient_id, Long _user_id, Long _doctor_id, Date _date, Time _time, String _description, Appointment_type _type)
    {
        this._id = _id;
        this._patient_id = _patient_id;
        this._user_id = _user_id;
        this._doctor_id = _doctor_id;
        //this._phone_num = _phone_num;
        this._date = _date;
        this._time = _time;
        this._description = _description;
        this._type = _type;
    }


    public Appointment()
    {

    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public Long get_patient_id()
    {
        return _patient_id;
    }

    public void set_patient_id(Long _patient_id)
    {
        this._patient_id = _patient_id;
    }

    public Long get_user_id()
    {
        return _user_id;
    }

    public void set_user_id(Long _user_id)
    {
        this._user_id = _user_id;
    }

    public Long get_doctor_id()
    {
        return _doctor_id;
    }

    public void set_doctor_id(Long _doctor_id)
    {
        this._doctor_id = _doctor_id;
    }

    public Date get_date()
    {
        return _date;
    }

    public void set_date(Date _date)
    {
        this._date = _date;
    }

    public Time get_time()
    {
        return _time;
    }

    public void set_time(Time _time)
    {
        this._time = _time;
    }

    public String get_description()
    {
        return _description;
    }

    public void set_description(String _description)
    {
        this._description = _description;
    }

    public Appointment_type get_type()
    {
        return _type;
    }

    public void set_type(Appointment_type _type)
    {
        this._type = _type;
    }

    @Override public String toString()
    {
        try
        {
            Person tmp_patient = this.get_patient();
            Person tmp_doctor = this.get_doctor();

            return  tmp_patient.get_name() + " " + this.get_date().toString() + " " + tmp_doctor.get_name() +
                    " " + tmp_doctor.get_last_name();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return "";
    }


    public Person get_patient() throws SQLException
    {
        Db_connection db = new Db_connection();

        return db.get_person_by_id(this._patient_id.toString());

    }

    public Person get_doctor() throws SQLException
    {
        Db_connection db = new Db_connection();

        return db.get_person_by_id(this._doctor_id.toString());

    }

}
