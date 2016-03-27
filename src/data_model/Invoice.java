package data_model;

/**
 * Created by victory on 3/26/16.
 */
public class Invoice
{
    private Long _id;
    private Long _consultation_id;
    private Long _user_id;
    private String _description;
    private float _sub_total;
    private float _iva;
    private float _total;


    public Invoice(Long _id, Long _consultation_id, Long _user_id, String _description, float _sub_total, float _iva, float _total)
    {
        this._id = _id;
        this._consultation_id = _consultation_id;
        this._user_id = _user_id;
        this._description = _description;
        this._sub_total = _sub_total;
        this._iva = _iva;
        this._total = _total;
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public Long get_consultation_id()
    {
        return _consultation_id;
    }

    public void set_consultation_id(Long _consultation_id)
    {
        this._consultation_id = _consultation_id;
    }

    public Long get_user_id()
    {
        return _user_id;
    }

    public void set_user_id(Long _user_id)
    {
        this._user_id = _user_id;
    }

    public String get_description()
    {
        return _description;
    }

    public void set_description(String _description)
    {
        this._description = _description;
    }

    public float get_sub_total()
    {
        return _sub_total;
    }

    public void set_sub_total(float _sub_total)
    {
        this._sub_total = _sub_total;
    }

    public float get_iva()
    {
        return _iva;
    }

    public void set_iva(float _iva)
    {
        this._iva = _iva;
    }

    public float get_total()
    {
        return _total;
    }

    public void set_total(float _total)
    {
        this._total = _total;
    }
}
