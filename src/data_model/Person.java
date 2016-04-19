package data_model;


import java.sql.Date;

/**
 * Created by victory on 3/26/16.
 */
public class Person
{
    private Long _id;
    private String _name;
    private String _last_name;
    private String _gender;
    private Date _birth_date;
    private Date _reg_date;
    private String _direction;
    private String _phone_num;


    public Person(Long id, String name, String last_name, String gender, Date birth_date, Date reg_date, String direction, String phone_num)
    {
        this._id = id;
        this._name = name;
        this._last_name = last_name;
        this._gender = gender;
        this._birth_date = birth_date;
        this._reg_date = reg_date;
        this._direction = direction;
        this._phone_num = phone_num;
    }

    public  Person()
    {
        this._id = new Long(0);
        this._name = new String();
        this._last_name = new String();
        this._gender = new String();
        this._birth_date = new Date(0);
        this._reg_date = new Date(0);
        this._direction = new String();
        this._phone_num = new String();
    }


    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long id)
    {
        this._id = id;
    }

    public String get_name()
    {
        return _name;
    }

    public void set_name(String name)
    {
        this._name = name;
    }

    public String get_last_name() { return _last_name; }

    public void set_last_name(String _last_name) { this._last_name = _last_name; }


    public String get_gender()
    {
        return _gender;
    }

    public void set_gender(String gender)
    {
        this._gender = gender;
    }

    public Date get_birth_date()
    {
        return _birth_date;
    }

    public void set_birth_date(Date birth_date)
    {
        this._birth_date = birth_date;
    }

    public Date get_reg_date()
    {
        return _reg_date;
    }

    public void set_reg_date(Date reg_date)
    {
        this._reg_date = reg_date;
    }

    public String get_direction()
    {
        return _direction;
    }

    public void set_direction(String direction)
    {
        this._direction = direction;
    }

    public String get_phone_num()
    {
        return _phone_num;
    }

    public void set_phone_num(String phone_num)
    {
        this._phone_num = phone_num;
    }

    @Override
    public String toString()
    {
        return this._name + " " + this._last_name ;
    }

}
