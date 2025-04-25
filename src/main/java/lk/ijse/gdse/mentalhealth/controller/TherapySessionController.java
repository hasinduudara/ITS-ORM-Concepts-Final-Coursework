package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.TherapySessionBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapySessionDTO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

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
    private TextField txtSessionID;

    @FXML
    private TextField txtSessionTime;

    @FXML
    private TextField txtTherapistID;

    private static TherapySessionController instance;

    // set therapist id to text field
    public TherapySessionController() {
        instance = this;
    }

    public static TherapySessionController getInstance() {
        return instance;
    }

    public void setProgramId(String id) {
        txtProgramID.setText(id);
    }

    public void setPatientId(String id) {
        txtPatientID.setText(id);
    }

    public void setTherapistID(String id) {
        txtTherapistID.setText(id);
    }

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    PaymentController paymentController = new PaymentController();

    private void generateNewId() {
        txtSessionID.setText(therapySessionBO.getNaxtSessionID());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUI("/view/PatientTable.fxml");
        generateNewId();
    }


    private void loadUI(String resource) {
        miniPane.getChildren().clear();
        try {
            miniPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load the UI", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnAddPatientOnAction(ActionEvent event) {
        loadUI("/view/PatientTable.fxml");
    }

    @FXML
    void btnAddProgramOnAction(ActionEvent event) {
        loadUI("/view/TherapyProgramTable.fxml");
    }

    @FXML
    void btnAddTherapistOnAction(ActionEvent event) {
        loadUI("/view/TherapistTable.fxml");
    }

    @FXML
    void btnCreateSessionOnAction(ActionEvent event) {
        try {
            // Step 1: Validate input fields
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
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        // Validate session ID
        if (txtSessionID.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Session ID cannot be empty", Alert.AlertType.ERROR);
            txtSessionID.requestFocus();
            return false;
        }

        // Validate patient ID
        if (txtPatientID.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Patient ID cannot be empty", Alert.AlertType.ERROR);
            txtPatientID.requestFocus();
            return false;
        }

        // Validate therapist ID
        if (txtTherapistID.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Therapist ID cannot be empty", Alert.AlertType.ERROR);
            txtTherapistID.requestFocus();
            return false;
        }

        // Validate date
        if (datePickerSessionDate.getValue() == null) {
            showAlert("Validation Error", "Session date must be selected", Alert.AlertType.ERROR);
            datePickerSessionDate.requestFocus();
            return false;
        }

        // Validate time
        try {
            LocalTime.parse(txtSessionTime.getText());
        } catch (Exception e) {
            showAlert("Validation Error", "Session time must be in a valid format (HH:MM)", Alert.AlertType.ERROR);
            txtSessionTime.requestFocus();
            return false;
        }
        return true;
    }

    private TherapySessionDTO collectSessionData() {
        TherapySessionDTO sessionDTO = new TherapySessionDTO();

        sessionDTO.setSessionId(txtSessionID.getText());
        sessionDTO.setPatientId(txtPatientID.getText());
        sessionDTO.setTherapistId(txtTherapistID.getText());
        sessionDTO.setProgramId(txtProgramID.getText());
        sessionDTO.setSessionDate(datePickerSessionDate.getValue());
        sessionDTO.setSessionTime(LocalTime.parse(txtSessionTime.getText()));

        return sessionDTO;
    }

    private boolean confirmSessionBooking() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Pay payment to confirm this session",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void navigateToPaymentForm(TherapySessionDTO sessionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Payment.fxml"));
            AnchorPane paymentPane = loader.load();

            PaymentController paymentController = loader.getController();
            paymentController.setSessionId(sessionDTO.getSessionId());
            paymentController.setParentController(this);

            therapySessionPage.getChildren().clear();
            therapySessionPage.getChildren().add(paymentPane);

            paymentController.savePaymentWithSession();

        } catch (IOException e) {
            showAlert("Error", "Failed to load payment form!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void handlePaymentComplete(TherapySessionDTO sessionDTO) {
        try {
            boolean isBooked = therapySessionBO.bookSession(
                    sessionDTO.getSessionId(),
                    sessionDTO.getPatientId(),
                    sessionDTO.getTherapistId(),
                    sessionDTO.getProgramId(),
                    sessionDTO.getSessionDate(),
                    sessionDTO.getSessionTime()
            );

            if (isBooked) {
                showAlert("Success", "Therapy session booked successfully", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showAlert("Error", "Failed to book session!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Error saving session: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void onPaymentCompleted() {
        try {
            navigateToSessionList();
        } catch (Exception e) {
            showAlert("Error", "Could not navigate to session list after payment", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void navigateToSessionList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TherapySessionTable.fxml"));
        AnchorPane pane = loader.load();
        therapySessionPage.getChildren().setAll(pane);
    }

    @FXML
    void btnSeeAllSessionsOnAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TherapySessionTable.fxml")));
            miniPane.getChildren().setAll(pane);
        } catch (IOException e) {
            showAlert("Error", "Failed to load session list!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtSessionID.clear();
        txtPatientID.clear();
        txtTherapistID.clear();
        txtProgramID.clear();
        datePickerSessionDate.setValue(null);
        txtSessionTime.clear();
    }


}
