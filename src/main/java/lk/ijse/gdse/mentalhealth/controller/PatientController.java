package lk.ijse.gdse.mentalhealth.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.PatientBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse.mentalhealth.dto.PatientDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.PatientTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PatientTM, String> colMedicalHistory;

    @FXML
    private TableColumn<PatientTM, String> colPatientGender;

    @FXML
    private TableColumn<PatientTM, String> colPatientID;

    @FXML
    private TableColumn<PatientTM, String> colPatientName;

    @FXML
    private TableColumn<PatientTM, String> colPatientPhoneNo;

    @FXML
    private ComboBox<String> comBoxPatientGender;

    @FXML
    private Label lblPatientID;

    @FXML
    private AnchorPane patientPage;

    @FXML
    private TableView<PatientTM> tblPatientManagement;

    @FXML
    private TextArea txtMedicalHistory;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPatientPhoneNo;

    private final PatientBO patientBO = new PatientBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPatientID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPatientPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPatientGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        comBoxPatientGender.getItems().addAll("Male", "Female");

        loadAllPatients();
        try {
            generateNewPatientId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNewPatientId() throws SQLException, ClassNotFoundException {
        lblPatientID.setText(patientBO.getNextPatientID());
    }

    private void loadAllPatients() {
        try {
            ArrayList<PatientDTO> patients = patientBO.loadAllPatient();
            ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

            for (PatientDTO patient : patients) {
                PatientTM patientTM = new PatientTM(
                        patient.getId(),
                        patient.getName(),
                        patient.getPhoneNumber(),
                        patient.getGender(),
                        patient.getMedicalHistory()
                );
                patientTMS.add(patientTM);
            }
            tblPatientManagement.setItems(patientTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load patients", Alert.AlertType.ERROR);
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
    void btnClearOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        PatientTM selectedPatient = tblPatientManagement.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert("Error", "Please select a patient to delete!", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this patient?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String patientId = selectedPatient.getId();
            try {
                boolean isDeleted = patientBO.deletePatient(patientId);
                if (isDeleted) {
                    showAlert("Success", "Patient deleted successfully!", Alert.AlertType.INFORMATION);
                    loadAllPatients();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete patient!", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("Error", "An error occurred while deleting the patient!", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblPatientID.getText();
        String name = txtPatientName.getText();
        String phoneNumber = txtPatientPhoneNo.getText();
        String gender = String.valueOf(comBoxPatientGender.getValue());
        String medicalHistory = txtMedicalHistory.getText();

        if (id.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || medicalHistory.isEmpty()) {
            showAlert("Error", "All fields are required", Alert.AlertType.ERROR);
            return;
        }

        PatientDTO patientDTO = new PatientDTO(id, name, phoneNumber, gender, medicalHistory);

        try {
            boolean isSaved = patientBO.savePatient(patientDTO);

            if (isSaved) {
                showAlert("Success", "Patient saved successfully", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save patient", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while saving the patient", Alert.AlertType.ERROR);
        }
        loadAllPatients();
        clearFields();
    }

    private void clearFields() throws SQLException, ClassNotFoundException {
//        lblPatientID.setText("");
        txtPatientName.clear();
        txtPatientPhoneNo.clear();
        comBoxPatientGender.getSelectionModel().clearSelection();
        txtMedicalHistory.clear();
        generateNewPatientId();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblPatientID.getText();
        String name = txtPatientName.getText();
        String phoneNumber = txtPatientPhoneNo.getText();
        String gender = String.valueOf(comBoxPatientGender.getValue());
        String medicalHistory = txtMedicalHistory.getText();

        if (id.isEmpty() || name.isEmpty() || phoneNumber.isEmpty() || medicalHistory.isEmpty()) {
            showAlert("Error", "All fields are required", Alert.AlertType.ERROR);
            return;
        }

        PatientDTO patientDTO = new PatientDTO(id, name, phoneNumber, gender, medicalHistory);

        try {
            boolean isUpdated = patientBO.updatePatient(patientDTO);

            if (isUpdated) {
                showAlert("Success", "Patient updated successfully", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update patient", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the patient", Alert.AlertType.ERROR);
        }
        loadAllPatients();
        try {
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comBoxPatientGenderOnAction(ActionEvent event) {

    }

    @FXML
    void tblPatientManagementOnMouseClicked(MouseEvent event) {
        PatientTM selectedPatient = tblPatientManagement.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            lblPatientID.setText(selectedPatient.getId());
            txtPatientName.setText(selectedPatient.getName());
            txtPatientPhoneNo.setText(selectedPatient.getPhoneNumber());
            comBoxPatientGender.setValue(selectedPatient.getGender());
            txtMedicalHistory.setText(selectedPatient.getMedicalHistory());
        }
    }

}
