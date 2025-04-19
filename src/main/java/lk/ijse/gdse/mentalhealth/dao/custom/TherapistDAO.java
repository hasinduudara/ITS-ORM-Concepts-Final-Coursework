package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.Therapist;

public interface TherapistDAO extends CrudDAO<Therapist> {
    Therapist getById(String therapistId);
}
