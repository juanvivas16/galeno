package data_model;

/**
 * Created by victory on 3/26/16.
 */
public class Consultation
{
    private Long _id;
    private Long _doctor_id;
    private Long _patient_id;
    private Long _appointment_id;
    private int _paid;
    //private int _done;


    public Consultation()
    {
        this._paid = 0; //initiate to not paid yet
        //this._done = 0; //undone
    }

    public Consultation(Long _id, Long _doctor_id, Long _patient_id, Long _appointment_id, int _paid )
    {
        this._id = _id;
        this._doctor_id = _doctor_id;
        this._patient_id = _patient_id;
        this._appointment_id = _appointment_id;
        this._paid = _paid;
        //this._done = _done;
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
    }

    public Long get_doctor_id()
    {
        return _doctor_id;
    }

    public void set_doctor_id(Long _doctor_id)
    {
        this._doctor_id = _doctor_id;
    }

    public Long get_patient_id()
    {
        return _patient_id;
    }

    public void set_patient_id(Long _patient_id)
    {
        this._patient_id = _patient_id;
    }

    public int get_paid()
    {
        return _paid;
    }

    public void set_paid(int _paid)
    {
        this._paid = _paid;
    }

    /*
    public int get_done()
    {
        return _done;
    }

    public void set_done(int _done)
    {
        this._done = _done;
    }
    */
    public Long get_appointment_id()
    {
        return _appointment_id;
    }

    public void set_appointment_id(Long _appointment_id)
    {
        this._appointment_id = _appointment_id;
    }
}
