package data_model;

/**
 * Created by victory on 3/26/16.
 */
public class Test_item
{
    private Long _id;
    private Long _test_id;
    private String _description;

    public Test_item()
    {
        this._id = new Long(0);
    }

    public Test_item(Long _id, Long _test_id, String _description)
    {
        this._id = _id;
        this._test_id = _test_id;
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

    public Long get_test_id()
    {
        return _test_id;
    }

    public void set_test_id(Long _test_id)
    {
        this._test_id = _test_id;
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
