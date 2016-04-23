package data_model;

/**
 * Created by victory on 3/26/16.
 */
public class Prescription_item
{
    private Long _id;
    private Long _prescription_id;
    private String _description;

    public Prescription_item()
    {
        this._id = new Long(0);
    }

    public Prescription_item(Long _id, Long _prescription_id, String _description)
    {
        this._id = _id;
        this._prescription_id = _prescription_id;
        this._description = _description;
    }

    @Override public String toString()
    {
        return this._description;
    }


    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public Long get_prescription_id()
    {
        return _prescription_id;
    }

    public void set_prescription_id(Long _prescription_id)
    {
        this._prescription_id = _prescription_id;
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
