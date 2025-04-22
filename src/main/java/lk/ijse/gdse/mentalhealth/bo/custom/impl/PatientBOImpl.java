package lk.ijse.gdse.mentalhealth.bo.custom.impl;

import lk.ijse.gdse.mentalhealth.bo.custom.PatientBO;
import lk.ijse.gdse.mentalhealth.dao.custom.PatientDAO;
import lk.ijse.gdse.mentalhealth.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse.mentalhealth.dto.PatientDTO;
import lk.ijse.gdse.mentalhealth.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {
    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public boolean savePatient(PatientDTO patientDTO) {
        return patientDAO.save(new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getPhoneNumber(),
                patientDTO.getGender(),
                patientDTO.getMedicalHistory(),
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) {
        return patientDAO.update(new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getPhoneNumber(),
                patientDTO.getGender(),
                patientDTO.getMedicalHistory(),
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean deletePatient(String id) throws Exception {
        return patientDAO.deleteByPK(id);
    }

    @Override
    public ArrayList<PatientDTO> loadAllPatient() throws SQLException, ClassNotFoundException {
        ArrayList<PatientDTO> patientDTOS = new ArrayList<>();
        ArrayList<Patient> patients = (ArrayList<Patient>) patientDAO.getAll();

        for (Patient patient : patients) {
            patientDTOS.add(
                    new PatientDTO(
                            patient.getId(),
                            patient.getName(),
                            patient.getPhoneNumber(),
                            patient.getGender(),
                            patient.getMedicalHistory()
                    ));

        }
        return patientDTOS;
    }

    @Override
    public String getNextPatientID() throws SQLException, ClassNotFoundException {
        return patientDAO.getNextId();
    }

    @Override
    public Patient findById(String id) {
        return null;
    }

    @Override
    public ArrayList<Patient> loadAllpatientsInCombo() {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            List<Patient> allPatients = patientDAO.getAll(); // DAO method
            patients.addAll(allPatients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

}
