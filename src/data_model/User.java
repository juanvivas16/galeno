package data_model;

import java.util.Date;
import java.util.Objects;

/**
 * Created by victory on 3/26/16.
 */
public class User extends Person
{
    private int _rol;
    private String _pass;


    public User(Long id, String name, char gender, Date birth_date, Date reg_date, String direction, String phone_num, Long _id, int _rol, String _pass)
    {
        super(id, name, gender, birth_date, reg_date, direction, phone_num);
        this._rol = _rol;
        this._pass = _pass;
    }


    public String get_pass()
    {
        return _pass;
    }

    public void set_pass(String _pass)
    {
        this._pass = _pass;
    }

    public int get_rol()
    {
        return _rol;
    }

    public void set_rol(int _rol)
    {
        this._rol = _rol;
    }
}
