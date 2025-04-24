package lk.ijse.gdse.mentalhealth.bo.custom;

import lk.ijse.gdse.mentalhealth.dto.TherapySessionDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionBO {
    boolean bookSession(String sessionId, String patientId, String therapistId, String programId, LocalDate date, LocalTime time);

    boolean bookSessionWithPayment(String sessionId, String patientId, String therapistId, String programId, LocalDate sessionDate, LocalTime sessionTime, String paymentId);

    ArrayList<TherapySessionDTO> loadAllSessions();

    TherapySessionDTO getSessionByPatientName(String name);

    String getNaxtSessionID();
}
