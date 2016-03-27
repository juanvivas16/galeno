package data_model;

import java.util.Date;

/**
 * Created by victory on 3/26/16.
 */
public class Queue
{
    private Long _id;
    private Long _paciente_id;
    private Long _doctor_id;
    private Date _date;
    private Long _user_id;
    private int _position;

    public Queue(Long _id, Long _paciente_id, Long _doctor_id, Date _date, Long _user_id, int _position)
    {
        this._id = _id;
        this._paciente_id = _paciente_id;
        this._doctor_id = _doctor_id;
        this._date = _date;
        this._user_id = _user_id;
        this._position = _position;
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public Long get_paciente_id()
    {
        return _paciente_id;
    }

    public void set_paciente_id(Long _paciente_id)
    {
        this._paciente_id = _paciente_id;
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

    public Long get_user_id()
    {
        return _user_id;
    }

    public void set_user_id(Long _user_id)
    {
        this._user_id = _user_id;
    }

    public int get_position()
    {
        return _position;
    }

    public void set_position(int _position)
    {
        this._position = _position;
    }
}
