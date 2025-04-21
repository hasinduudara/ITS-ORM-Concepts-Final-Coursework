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
import lk.ijse.gdse.mentalhealth.bo.custom.TherapistBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapistBOImpl;
import lk.ijse.gdse.mentalhealth.config.FactoryConfiguration;
import lk.ijse.gdse.mentalhealth.dto.TherapistDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.TherapistTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistManagementController implements Initializable {

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
    private TableColumn<TherapistTM, String> colTherapistAvailability;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistID;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistName;

    @FXML
    private TableColumn<TherapistTM, String> colTherapistSpecialty;

    @FXML
    private Label lblTherapistID;

    @FXML
    private TableView<Object> tblTherapistManagement;

    @FXML
    private TextField textName;

    @FXML
    private TextField textSpecialty;

    @FXML
    private TextField txtAvailability;

    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    private final TherapistBO therapistBO = new TherapistBOImpl();

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        TherapistTM selectedTherapist = (TherapistTM) tblTherapistManagement.getSelectionModel().getSelectedItem();
        if (selectedTherapist == null) {
            showAlert("Warning", "Please select a therapist to delete!", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = therapistBO.deleteTherapist(selectedTherapist.getTherapistID());

                if (isDeleted) {
                    showAlert("Success", "Therapist deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete therapist!", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadTherapists();
            clear();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String name = textName.getText();
        String specialty = textSpecialty.getText();
        String availability = txtAvailability.getText();
        String id = lblTherapistID.getText();

        if (name.isEmpty() || specialty.isEmpty() || availability.isEmpty()) {
            showAlert("Error", "Please fill all fields!", Alert.AlertType.ERROR);
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, availability);
        try {
            boolean isSaved = therapistBO.saveTherapist(therapistDTO);

            if (isSaved) {
                showAlert("Success", "Therapist saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save therapist!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadTherapists();
        clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String name = textName.getText();
        String specialty = textSpecialty.getText();
        String availability = txtAvailability.getText();
        String id = lblTherapistID.getText();

        if (name.isEmpty() || specialty.isEmpty() || availability.isEmpty()) {
            showAlert("Error", "Please fill all fields!", Alert.AlertType.ERROR);
            return;
        }

        try {
            TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, availability);

            boolean isUpdated = therapistBO.updateTherapist(therapistDTO);
            if (isUpdated) {
                showAlert("Success", "Therapist updated successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update therapist!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadTherapists();
        clear();
    }

    @FXML
    void tblTherapistManagementOnMouseClicked(MouseEvent event) {
        TherapistTM selectedTherapist = (TherapistTM) tblTherapistManagement.getSelectionModel().getSelectedItem();
        if (selectedTherapist != null) {
            lblTherapistID.setText(selectedTherapist.getTherapistID());
            textName.setText(selectedTherapist.getTherapistName());
            textSpecialty.setText(selectedTherapist.getSpecialization());
            txtAvailability.setText(selectedTherapist.getAvailability());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the table columns and other UI components here
        colTherapistID.setCellValueFactory(new PropertyValueFactory<>("therapistID"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colTherapistSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colTherapistAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data into the table
        loadTherapists();
//        generateNewId();
    }

    private void generateNewId() {
        lblTherapistID.setText(therapistBO.getNaxtTherapistID());
    }

    private void loadTherapists() {
        try {
            ArrayList<TherapistDTO> therapists = therapistBO.loadAllTherapists();
            ObservableList<Object> therapistTMList = FXCollections.observableArrayList();

            for (TherapistDTO therapistDTO : therapists) {
                TherapistTM therapistTM = new TherapistTM(
                        therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getAvailability()
                );
                therapistTMList.add(therapistTM);
            }
            tblTherapistManagement.setItems(therapistTMList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clear() {
        textName.clear();
        textSpecialty.clear();
        txtAvailability.clear();
        generateNewId();
    }
}
