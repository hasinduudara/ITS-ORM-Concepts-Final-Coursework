package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TherapistManagementController {

    @FXML
    private AnchorPane AnchorPaneTherapistManagement;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colTherapistAvailability;

    @FXML
    private TableColumn<?, ?> colTherapistID;

    @FXML
    private TableColumn<?, ?> colTherapistName;

    @FXML
    private TableColumn<?, ?> colTherapistSpecialty;

    @FXML
    private Label lblTherapistID;

    @FXML
    private TableView<?> tblTherapistManagement;

    @FXML
    private TextField textName;

    @FXML
    private TextField textSpecialty;

    @FXML
    private TextField txtAvailability;

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
    void tblTherapistManagementOnMouseClicked(MouseEvent event) {

    }

}
