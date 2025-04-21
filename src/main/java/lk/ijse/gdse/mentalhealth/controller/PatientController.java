package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PatientController {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colMedicalHistory;

    @FXML
    private TableColumn<?, ?> colPatientGender;

    @FXML
    private TableColumn<?, ?> colPatientID;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colPatientPhoneNo;

    @FXML
    private ComboBox<?> comBoxPatientGender;

    @FXML
    private Label lblPatientID;

    @FXML
    private AnchorPane patientPage;

    @FXML
    private TableView<?> tblPatientManagement;

    @FXML
    private TextArea txtMedicalHistory;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPatientPhoneNo;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void comBoxPatientGenderOnAction(ActionEvent event) {

    }

    @FXML
    void tblPatientManagementOnMouseClicked(MouseEvent event) {

    }

}
