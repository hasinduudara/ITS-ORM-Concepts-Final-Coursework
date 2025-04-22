package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TherapySessionController {

    @FXML
    private Button btnAddPatient;

    @FXML
    private Button btnAddProgram;

    @FXML
    private Button btnAddTherapist;

    @FXML
    private Button btnCreateSession;

    @FXML
    private Button btnSeeAllSessions;

    @FXML
    private DatePicker datePickerSessionDate;

    @FXML
    private Label lblSessionID;

    @FXML
    private AnchorPane miniPane;

    @FXML
    private AnchorPane therapySessionPage;

    @FXML
    private TextField txtPatientID;

    @FXML
    private TextField txtProgramID;

    @FXML
    private TextField txtSessionTime;

    @FXML
    private TextField txtTherapistID;

    private static TherapySessionController instance;

    public TherapySessionController() {
        instance = this;
    }

    public static TherapySessionController getInstance() {
        return instance;
    }

    public void setTherapistId(String therapistID) {
        txtTherapistID.setText(therapistID);
    }

    public void setPatientId(String patientID) {
        txtPatientID.setText(patientID);
    }

    public void setProgramId(String programID) {
        txtProgramID.setText(programID);
    }

    @FXML
    void btnAddPatientOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddProgramOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddTherapistOnAction(ActionEvent event) {

    }

    @FXML
    void btnCreateSessionOnAction(ActionEvent event) {

    }

    @FXML
    void btnSeeAllSessionsOnAction(ActionEvent event) {

    }

}
