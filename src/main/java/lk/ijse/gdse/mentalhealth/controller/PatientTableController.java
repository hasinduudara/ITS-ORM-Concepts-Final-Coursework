package lk.ijse.gdse.mentalhealth.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.PatientBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse.mentalhealth.dto.PatientDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.PatientTableTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientTableController implements Initializable {

    @FXML
    private TableColumn<PatientTableTM, String> colGender;

    @FXML
    private TableColumn<PatientTableTM, String> colMedicalHistory;

    @FXML
    private TableColumn<PatientTableTM, String> colName;

    @FXML
    private TableColumn<PatientTableTM, String> colPatientID;

    @FXML
    private TableColumn<PatientTableTM, String> colPhoneNo;

    @FXML
    private AnchorPane patientTablePage;

    @FXML
    private TableView<PatientTableTM> tblMiniPatient;

    private final PatientBO patientBO = new PatientBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatientID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        loadAllPatients();
    }

    private void loadAllPatients() {
        try {
            ArrayList<PatientDTO> patients = patientBO.loadAllPatient();
            ObservableList<PatientTableTM> patientTableTMS = FXCollections.observableArrayList();

            for (PatientDTO patient : patients) {
                PatientTableTM patientTableTM = new PatientTableTM(
                        patient.getId(),
                        patient.getName(),
                        patient.getPhoneNumber(),
                        patient.getGender(),
                        patient.getMedicalHistory()
                );
                patientTableTMS.add(patientTableTM);
            }
            tblMiniPatient.setItems(patientTableTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load patients: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void tblMiniPatientOnMouseClicked(MouseEvent event) {
        PatientTableTM patientTableTM = tblMiniPatient.getSelectionModel().getSelectedItem();

        if (patientTableTM == null) {
            showAlert("Warning", "Please select a patient", Alert.AlertType.WARNING);
        } else {
            TherapySessionController.getInstance().setPatientId(patientTableTM.getId());
        }
    }

}
