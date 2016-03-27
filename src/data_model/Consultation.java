package data_model;

/**
 * Created by victory on 3/26/16.
 */
public class Consultation
{
    private Long _id;
    private Long _doctor_id;
    private Long _patient_id;

    public Consultation(Long _id, Long _doctor_id, Long _patient_id)
    {
        this._id = _id;
        this._doctor_id = _doctor_id;
        this._patient_id = _patient_id;
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
}
