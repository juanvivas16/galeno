package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import data_model.Appointment;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by juancho on 09/05/16.
 */
public class AppointmentTest
{
    Appointment app = new Appointment();

    @org.junit.Test
    public void set_id() throws Exception
    {
        app.set_id(new Long(-1));
        assertEquals(new Long(0), app.get_id());

    }

    @org.junit.Test
    public void set_patient_id() throws Exception
    {
        app.set_patient_id(new Long(-1));
        assertEquals(new Long(0), app.get_patient_id());
    }

    @org.junit.Test
    public void set_user_id() throws Exception
    {
        app.set_user_id(new Long(-1));
        assertEquals(new Long(0), app.get_user_id());
    }

    @org.junit.Test
    public void set_doctor_id() throws Exception
    {
        app.set_doctor_id(new Long(-1));
        assertEquals(new Long(0), app.get_doctor_id());
    }

    @org.junit.Test
    public void set_date() throws Exception
    {
        app.set_date(Date.valueOf("2016-03-26"));
        assertEquals(Date.valueOf(LocalDate.now()), app.get_date());

//        app.set_date(Date.valueOf("2016-03-26"));
//        assertEquals(Date.valueOf("2016-05-14"), app.get_date());
    }

    @org.junit.Test
    public void set_time() throws Exception
    {
        app.set_time(Time.valueOf("07:00:00"));
        assertEquals(Time.valueOf("08:00:00"),app.get_time());

//        app.set_time(Time.valueOf("07:00:00"));
//        assertEquals(Time.valueOf("09:00:00"),app.get_time());

    }

}