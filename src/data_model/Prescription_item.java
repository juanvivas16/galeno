package data_model;

/**
 * Created by victory on 3/26/16.
 */
public class Prescription_item
{
    private Long _id;
    private String _name;
    private Prescription_item_type _type;
    private String _instruction;


    public Prescription_item(Long _id, String _name, Prescription_item_type _type, String _instruction)
    {
        this._id = _id;
        this._name = _name;
        this._type = _type;
        this._instruction = _instruction;
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public String get_name()
    {
        return _name;
    }

    public void set_name(String _name)
    {
        this._name = _name;
    }

    public Prescription_item_type get_type()
    {
        return _type;
    }

    public void set_type(Prescription_item_type _type)
    {
        this._type = _type;
    }

    public String get_instruction()
    {
        return _instruction;
    }

    public void set_instruction(String _instruction)
    {
        this._instruction = _instruction;
    }
}
