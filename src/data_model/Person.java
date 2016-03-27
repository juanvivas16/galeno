package data_model;

import java.util.Date;

/**
 * Created by victory on 3/26/16.
 */
public class Person
{
    private Long _id;
    private String _name;
    private char _gender;
    private Date _birth_date;
    private Date _reg_date;
    private String _direction;
    private String _phone_num;


    public Person(Long id, String name, char gender, Date birth_date, Date reg_date, String direccion, String phone_num)
    {
        this._id = id;
        this._name = name;
        this._gender = gender;
        this._birth_date = birth_date;
        this._reg_date = reg_date;
        this._direction = direccion;
        this._phone_num = phone_num;
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

    public char get_gender()
    {
        return _gender;
    }

    public void set_gender(char gender)
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
}
