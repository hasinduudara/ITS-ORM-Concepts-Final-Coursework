package lk.ijse.gdse.mentalhealth.dao.custom;

import lk.ijse.gdse.mentalhealth.entity.TherapySession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {
    TherapySession findById(String sessionId);

    TherapySession getSessionByPatientName(String therapistId, String date, String time);

    List<TherapySession> findByTherapistAndTime(String therapistId, LocalDate date, LocalTime time) ;

    TherapySession getSessionByPatientName(String name);

}
