package lk.ijse.gdse.mentalhealth.bo.custom;

import lk.ijse.gdse.mentalhealth.dto.PatientDTO;
import lk.ijse.gdse.mentalhealth.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PatientBO {
    boolean savePatient(PatientDTO patientDTO);
    boolean updatePatient(PatientDTO patientDTO);
    boolean deletePatient(String id) throws Exception;
    ArrayList<PatientDTO> loadAllPatient() throws SQLException, ClassNotFoundException ;
    String getNextPatientID() throws SQLException, ClassNotFoundException;
    Patient findById(String id);
    ArrayList<Patient> loadAllpatientsInCombo();

}
