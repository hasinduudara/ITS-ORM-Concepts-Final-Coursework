package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.TherapySessionBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.gdse.mentalhealth.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.mentalhealth.dto.TherapySessionDTO;
import lk.ijse.gdse.mentalhealth.entity.Therapist;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

    @FXML
    private Button btnAddPatient;

    @FXML
    private TextField txtSessionID;

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

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    PaymentController paymentController = new PaymentController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUI("/view/PatientTable.fxml");
//        generateSessionID();
    }

    private void generateSessionID() {
        try {
            String sessionId = therapySessionBO.getNaxtSessionID();
            lblSessionID.setText(sessionId);
        } catch (Exception e) {
            showAlert("Error", "Failed to generate session ID.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddPatientOnAction(ActionEvent event) {
        loadUI("/view/PatientTable.fxml");
//        clearFields();
    }

    @FXML
    void btnAddProgramOnAction(ActionEvent event) {
        loadUI("/view/TherapyProgramTable.fxml");
//        clearFields();
    }

    @FXML
    void btnAddTherapistOnAction(ActionEvent event) {
        loadUI("/view/TherapistTable.fxml");
//        clearFields();
    }

    @FXML
    void btnCreateSessionOnAction(ActionEvent event) {
        try {
            // Step 1: Validate inputs
            if (!validateInputs()) {
                return;
            }

            // Step 2: Collect session data
            TherapySessionDTO sessionDTO = collectSessionData();
            handlePaymentComplete(sessionDTO);

            // Step 3: Ask for confirmation
            if (confirmSessionBooking()) {
                // Step 4: Navigate to payment form
                navigateToPaymentForm(sessionDTO);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to create session.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        System.out.println("Session ID: " + txtSessionID.getText());
        System.out.println("Therapist ID: " + txtTherapistID.getText());
        System.out.println("Program ID: " + txtProgramID.getText());
    }

    private void handlePaymentComplete(TherapySessionDTO sessionDTO) {
        try {
            // Retrieve the therapist object (ensure this method fetches the therapist correctly)
            Therapist therapist = therapySessionBO.getTherapistById(sessionDTO.getTherapistId());

            if (therapist == null) {
                showAlert("Error", "Therapist not found. Please check the Therapist ID.", Alert.AlertType.ERROR);
                System.err.println("Therapist ID not found: " + sessionDTO.getTherapistId());
                return;
            }

            // Update therapist availability if needed
            therapist.setAvailability("Unavailable");

            boolean isBooked = therapySessionBO.bookSession(
                    sessionDTO.getSessionId(),
                    sessionDTO.getPatientId(),
                    sessionDTO.getProgramId(),
                    sessionDTO.getTherapistId(),
                    sessionDTO.getSessionDate(),
                    sessionDTO.getSessionTime()
            );

            if (isBooked) {
                showAlert("Success", "Session booked successfully!", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showAlert("Error", "Failed to book session.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Error saving session: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnSeeAllSessionsOnAction(ActionEvent event) throws IOException {
        therapySessionPage.getChildren().clear();
        therapySessionPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TherapySessionTable.fxml")));
    }

    private void loadUI(String resource) {
        miniPane.getChildren().clear();
        AnchorPane pane = null;
        try {
            miniPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load the page.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtPatientID.clear();
        txtProgramID.clear();
        txtSessionTime.clear();
        txtTherapistID.clear();
        datePickerSessionDate.setValue(null);
    }

    private boolean validateInputs() {
        // validate patient id
        if (txtPatientID.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Patient ID cannot be empty.", Alert.AlertType.ERROR);
            txtPatientID.requestFocus();
            return false;
        }

        // validate program id
        if (txtProgramID.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Program ID cannot be empty.", Alert.AlertType.ERROR);
            txtProgramID.requestFocus();
            return false;
        }

        // validate therapist id
        if (txtTherapistID.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Therapist ID cannot be empty.", Alert.AlertType.ERROR);
            txtTherapistID.requestFocus();
            return false;
        }

        // validate session time
        if (txtSessionTime.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Session time cannot be empty.", Alert.AlertType.ERROR);
            txtSessionTime.requestFocus();
            return false;
        }

        // validate time
        try {
            LocalTime.parse(txtSessionTime.getText());
        } catch (Exception e) {
            showAlert("Validation Error", "Invalid time format. Use HH:mm.", Alert.AlertType.ERROR);
            txtSessionTime.requestFocus();
            return false;
        }
        return true;
    }

    private boolean confirmSessionBooking() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Pay payment to confirm this session",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private TherapySessionDTO collectSessionData() {
        TherapySessionDTO sessionDTO = new TherapySessionDTO();

//        sessionDTO.setSessionId(lblSessionID.getText());
        sessionDTO.setSessionId(txtSessionID.getText());
        sessionDTO.setPatientId(txtPatientID.getText());
        sessionDTO.setProgramId(txtProgramID.getText());
        sessionDTO.setTherapistId(txtTherapistID.getText());
        sessionDTO.setSessionDate(datePickerSessionDate.getValue());
        sessionDTO.setSessionTime(LocalTime.parse(txtSessionTime.getText()));

        return sessionDTO;
    }

    private void navigateToPaymentForm(TherapySessionDTO sessionDTO) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Payment.fxml"));
            AnchorPane paymentForm = loader.load();

            // Get the actual controller instance associated with the FXML
            PaymentController paymentController = loader.getController();

            // Set session details
            paymentController.setSessionId(sessionDTO.getSessionId());
            paymentController.setParentController(this);

            // Now display the pane
            therapySessionPage.getChildren().clear();

            // Simulate the user completing the form and saving payment
            paymentController.savePaymentWithSession();

        } catch (IOException e) {
            showAlert("Error", "Failed to load payment form.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void onPaymentCompleted() {
        try {
            navigateToSessionList();
        } catch (IOException e) {
            showAlert("Error", "Failed to navigate to session list.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void navigateToSessionList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TherapySessionTable.fxml"));
        AnchorPane pane = loader.load();
        therapySessionPage.getChildren().clear();
    }

//    private TherapySessionDTO collectSessionDTO() {
//        TherapySessionDTO sessionDTO = new TherapySessionDTO();
//        sessionDTO.setSessionId(lblSessionID.getText());
//        sessionDTO.setPatientId(txtPatientID.getText());
//        sessionDTO.setProgramId(txtProgramID.getText());
//        sessionDTO.setTherapistId(txtTherapistID.getText());
//        sessionDTO.setSessionDate(String.valueOf(datePickerSessionDate.getValue()));
//        sessionDTO.setSessionTime(String.valueOf(LocalTime.parse(txtSessionTime.getText())));
//        return sessionDTO;
//    }
}
