package data_model;

import java.util.ArrayList;

/**
 * Created by victory on 3/26/16.
 */
public class Test
{
    private Long _id;
    private Long _consultation_id;
    private ArrayList<Test_item> _items;


    public Test(Long _id, Long _consultation_id, ArrayList<Test_item> _items)
    {
        this._id = _id;
        this._consultation_id = _consultation_id;
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

    public Long get_consultation_id()
    {
        return _consultation_id;
    }

    public void set_consultation_id(Long _consultation_id)
    {
        this._consultation_id = _consultation_id;
    }

    public ArrayList<Test_item> get_items()
    {
        return _items;
    }

    public void set_items(ArrayList<Test_item> _items)
    {
        this._items = _items;
    }

    //todo insert test item
}
