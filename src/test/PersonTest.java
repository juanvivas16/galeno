package test;

import org.junit.Test;
import data_model.Person;

import static org.junit.Assert.*;

/**
 * Created by juancho on 11/05/16.
 */
public class PersonTest
{

    Person per = new Person();

    @Test
    public void set_id() throws Exception
    {
        per.set_id(new Long(-1));
        assertEquals(new Long(0), per.get_id());
    }

    @Test
    public void set_name() throws Exception
    {
        per.set_name("");
        assertEquals(new String("empty"), per.get_name());

//        per.set_name(null);
//        assertEquals(new String("null"),per.get_name());

    }

    @Test
    public void set_last_name() throws Exception
    {
        per.set_last_name("");
        assertEquals(new String("empty"),per.get_last_name());
    }

    @Test
    public void set_gender() throws Exception
    {
        per.set_gender("");
        assertEquals(new String("M"),per.get_gender());
    }

    @Test
    public void set_direction() throws Exception
    {
        per.set_direction("");
        assertEquals(new String("empty"),per.get_direction());
    }

    @Test
    public void set_phone_num() throws Exception
    {
        per.set_phone_num("");
        assertEquals(new String("empty"),per.get_phone_num());
    }

}