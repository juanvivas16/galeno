package data_model;

import java.util.ArrayList;

/**
 * Created by victory on 3/26/16.
 */
public class Record
{
    private Long _id;
    private ArrayList<Record_item> _items;

    public Record(Long _id, ArrayList<Record_item> _items)
    {
        this._id = _id;
        this._items = _items;
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public ArrayList<Record_item> get_items()
    {
        return _items;
    }

    public void set_items(ArrayList<Record_item> _items)
    {
        this._items = _items;
    }


    // todo insert item in _items
}
