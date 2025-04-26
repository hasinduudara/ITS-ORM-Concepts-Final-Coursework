package lk.ijse.gdse.mentalhealth.bo.custom.impl;

import lk.ijse.gdse.mentalhealth.bo.custom.TherapySessionBO;
import lk.ijse.gdse.mentalhealth.dao.custom.PatientDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapistDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapySessionDTO;
import lk.ijse.gdse.mentalhealth.entity.Therapist;
import lk.ijse.gdse.mentalhealth.entity.TherapyProgram;
import lk.ijse.gdse.mentalhealth.entity.TherapySession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {
    private final TherapySessionDAO sessionDAO = new TherapySessionDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();
    private final TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();
    private final TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public boolean bookSession(String sessionId, String patientId, String therapistId, String programId, LocalDate date, LocalTime time) {
        // First verify therapist exists
//        Therapist therapist = therapistDAO.findById(therapistId);
//        if (therapist == null) {
//            System.out.println("Therapist not found with ID: " + therapistId);
//            return false;
//        }

        // Check for conflicts
        List<TherapySession> existing = sessionDAO.findByTherapistAndTime(therapistId, date, time);
        if (!existing.isEmpty()) {
            System.out.println("Therapist is not available at this time.");
            return false;
        }

        TherapySession session = new TherapySession();
        session.setSessionId(sessionId);
        session.setSessionDate(date);
        session.setSessionTime(time);
        session.setStatus("BOOKED");
        session.setPatient(patientDAO.findById(patientId));
        session.setTherapist(therapistDAO.findById(therapistId));
        TherapyProgram therapyProgram = therapyProgramDAO.findById(programId);
        session.setTherapyProgram(therapyProgram);
        System.out.println(" ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo " + therapistDAO.findById(therapistId));

        boolean sessionSaved = sessionDAO.save(session);
        if (!sessionSaved) {
            System.out.println("Failed to save therapy session.");
            return false;
        }

//        Therapist therapist = session.getTherapist();
//        therapist.setAvailability("BUSY");
//        therapistDAO.update(therapist);

        return true;
    }

    @Override
    public boolean bookSessionWithPayment(String sessionId, String patientId, String therapistId, String programId, LocalDate sessionDate, LocalTime sessionTime, String paymentId) {
        return false;
    }

    @Override
    public ArrayList<TherapySessionDTO> loadAllSessions() {
        ArrayList<TherapySessionDTO> sessionDTOS = new ArrayList<>();
        ArrayList<TherapySession> sessions = (ArrayList<TherapySession>) sessionDAO.getAll();

        if (sessions == null || sessions.isEmpty()) {
            System.out.println("No therapy sessions found.");
            return sessionDTOS;
        }

        for (TherapySession session : sessions) {
            String therapistID = (session.getTherapist() != null) ? session.getTherapist().getTherapistID() : "N/A";

            sessionDTOS.add(new TherapySessionDTO(
                    session.getSessionId(),
                    session.getSessionDate(),
                    session.getSessionTime(),
                    session.getStatus(),
                    session.getPatient().getId(),
                    session.getTherapyProgram().getProgramId(),
                    therapistID
            ));
        }
        return sessionDTOS;
    }

    @Override
    public TherapySessionDTO getSessionByPatientName(String name) {
        TherapySession session = sessionDAO.getSessionByPatientName(name);

        if (session != null) {
            return new TherapySessionDTO(
                    session.getSessionId(),
                    session.getPatient().getId(),
                    session.getTherapist().getTherapistID(),
                    session.getTherapyProgram().getProgramId(),
                    session.getSessionDate(),
                    session.getSessionTime(),
                    session.getStatus()
            );
        } else {
            return null;
        }
    }

    @Override
    public String getNaxtSessionID() {
        return sessionDAO.getNextId();
    }

    @Override
    public Therapist getTherapistById(String therapistId) {
        return therapistDAO.findById(therapistId);
    }
}
