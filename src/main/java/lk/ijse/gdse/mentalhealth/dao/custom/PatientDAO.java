package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.Patient;

public interface PatientDAO extends CrudDAO<Patient> {
    Patient findById(String id);
}
