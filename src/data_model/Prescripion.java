package data_model;

import java.util.ArrayList;

/**
 * Created by victory on 3/26/16.
 */
public class Prescripion
{
    private Long _id;
    private ArrayList<Prescription_item> _items;


    public Prescripion(ArrayList<Prescription_item> _items, Long _id)
    {
        this._items = _items;
        this._id = _id;
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public ArrayList<Prescription_item> get_items()
    {
        return _items;
    }

    public void set_items(ArrayList<Prescription_item> _items)
    {
        this._items = _items;
    }

    //todo insert item
}
