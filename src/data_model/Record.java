package data_model;

import java.sql.Date;

/**
 * Created by victory on 3/26/16.
 */
public class Record
{
    private Long _id;
    private Long _patient_id;
    private Long _consultation_id;
    private Long _user_id; //medic id
    private Date _visit_date;
    private String _description;

    public Record()
    {
        //set default date to actual date (today)
        java.util.Date date = new java.util.Date();
        this._visit_date = new Date(date.getTime());
    }

    public Record(Long _id, Long patient_id, Long consultation_id, Long _record_id, Long _user_id, Date _visit_date, String _description)
    {
        this._id = _id;
        this._consultation_id = consultation_id;
        this._patient_id = patient_id;
        this._user_id = _user_id;
        this._visit_date = _visit_date;
        this._description = _description;
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

    public Date get_visit_date()
    {
        return _visit_date;
    }

    public void set_visit_date(Date _visit_date)
    {
        this._visit_date = _visit_date;
    }

    public String get_description()
    {
        return _description;
    }

    public void set_description(String _description)
    {
        this._description = _description;
    }

    public Long get_consultation_id()
    {
        return _consultation_id;
    }

    public void set_consultation_id(Long _consultation_id)
    {
        this._consultation_id = _consultation_id;
    }


    @Override public String toString()
    {
        return this._visit_date.toString() + " " + this._description;
    }

}
