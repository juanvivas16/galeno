package data_model;

import java.util.Date;

/**
 * Created by victory on 3/26/16.
 */
public class Record_item
{
    private Long _id;
    private Long _record_id;
    private Long _user_id;
    private Date _visit_date;
    private String _description;

    public Record_item(Long _id, Long _record_id, Long _user_id, Date _visit_date, String _description)
    {
        this._id = _id;
        this._record_id = _record_id;
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

    public Long get_record_id()
    {
        return _record_id;
    }

    public void set_record_id(Long _record_id)
    {
        this._record_id = _record_id;
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
}
