package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.TherapyProgram;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {
    TherapyProgram findById(String id);
}
