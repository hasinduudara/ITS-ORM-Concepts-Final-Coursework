package lk.ijse.gdse.mentalhealth.controller;

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
import lk.ijse.gdse.mentalhealth.bo.custom.PaymentBO;
import lk.ijse.gdse.mentalhealth.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.PaymentBOImpl;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.gdse.mentalhealth.dto.PaymentDTO;
import lk.ijse.gdse.mentalhealth.entity.Patient;
import lk.ijse.gdse.mentalhealth.view.tdm.PaymentTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private DatePicker DatePickerPaymentDate;

    @FXML
    private Button btnCompletePayment;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPatient;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentID;

    @FXML
    private TableColumn<PaymentTM, String> colSessionID;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    @FXML
    private ComboBox<String> comBoxPaymentStatus;

    @FXML
    private ComboBox<String> comBoxSelectPatient;

    @FXML
    private Label lblPaymentID;

    @FXML
    private Label lblSessionID;

    @FXML
    private AnchorPane paymentPage;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtPaymentAmount;

    private Map<String, String> patientNameIdMap = new HashMap<>();

    private static PaymentController instance;

    public PaymentController() {
        this.instance = this;
    }

    public static PaymentController getInstance() {
        return instance;
    }

    public void setSessionId(String sessionId) {
        this.lblSessionID.setText(sessionId);
    }

    private TherapySessionController parentController;

    public void setParentController(TherapySessionController controller) {
        this.parentController = controller;
    }

    private final TherapyProgramBO programBO = new TherapyProgramBOImpl();
    private final PaymentBO paymentBO = new PaymentBOImpl();
    private final PatientBO patientBO = new PatientBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colSessionID.setCellValueFactory(new PropertyValueFactory<>("sessionId"));

        generateNewPaymentID();
        loadPatientCombo();
        loadPayments();

        comBoxPaymentStatus.getItems().addAll("PENDING", "COMPLETED");
    }

    private void loadPatientCombo() {
        try {
            ArrayList<Patient> allPatients = patientBO.loadAllpatientsInCombo();

            patientNameIdMap.clear();

            for (Patient patient : allPatients) {
                String patientName = patient.getName();
                patientNameIdMap.put(patientName, patient.getId());
            }

            comBoxSelectPatient.getItems().setAll(patientNameIdMap.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load patients", Alert.AlertType.ERROR);
        }
    }

    private void loadPayments() {
        try {
            ArrayList<PaymentDTO> payments = paymentBO.loadAllPayments();
            ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

            for (PaymentDTO payment : payments) {
                PaymentTM paymentTM = new PaymentTM(
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getStatus(),
                        payment.getPatientId(),
                        payment.getSessionId()
                );
                paymentTMS.add(paymentTM);
            }
            tblPayment.setItems(paymentTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load payments", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void generateNewPaymentID() {
        lblPaymentID.setText(paymentBO.getNextPaymentID());
    }

    @FXML
    void btnCompletePaymentOnAction(ActionEvent event) {
        boolean isSave = savePaymentWithSession();
        if (isSave) {
            showAlert("Success", "Payment completed successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to complete payment!", Alert.AlertType.ERROR);
        }
    }

    public boolean savePaymentWithSession() {
        String paymentId = lblPaymentID.getText();
        String sessionId = lblSessionID.getText();
        String paymentDate = DatePickerPaymentDate.getValue().toString();
        double amount = Double.parseDouble(txtPaymentAmount.getText());
        String status = comBoxPaymentStatus.getValue();
        String selecteName = comBoxSelectPatient.getValue();

        if (paymentId.isEmpty() || sessionId.isEmpty() || selecteName.isEmpty() || paymentDate == null || status.isEmpty() || amount < 0) {
            showAlert("Warning", "Please fill all fields correctly!", Alert.AlertType.ERROR);
            return false;
        }

//        double amount;

        try {
            amount = Double.parseDouble(txtPaymentAmount.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount entered!", Alert.AlertType.ERROR);
            return false;
        }

        // Get the patient ID using the selected name from the map
        String patientId = patientNameIdMap.get(comBoxSelectPatient.getValue());

        PaymentDTO paymentDTO = new PaymentDTO(paymentId, amount, paymentDate, status, patientId, sessionId);
        boolean isPaymentSaved = paymentBO.savePayment(paymentDTO);

        if (isPaymentSaved) {
            showAlert("Success", "Payment saved successfully!", Alert.AlertType.INFORMATION);
            if (parentController != null) {
                parentController.onPaymentCompleted();
            }
            return true;
        } else {
            showAlert("Error", "Failed to save payment!", Alert.AlertType.ERROR);
            return false;
        }
    }

    @FXML
    void tblPaymentOnMouseClicked(MouseEvent event) {
        PaymentTM selectedPayment = tblPayment.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            lblPaymentID.setText(selectedPayment.getPaymentId());
            txtPaymentAmount.setText(String.valueOf(selectedPayment.getAmount()));
            DatePickerPaymentDate.setValue(java.time.LocalDate.parse(selectedPayment.getPaymentDate()));
            comBoxSelectPatient.setValue(patientNameIdMap.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(selectedPayment.getPatientId()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null));
            comBoxPaymentStatus.setValue(selectedPayment.getStatus());
        }
    }

}
