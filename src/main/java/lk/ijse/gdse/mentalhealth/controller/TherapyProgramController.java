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
import lk.ijse.gdse.mentalhealth.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapyProgramDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.TherapyProgramTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapyProgramTM, Integer> colDuration;

    @FXML
    private TableColumn<TherapyProgramTM, Double> colFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> colProgramID;

    @FXML
    private TableColumn<TherapyProgramTM, String> colProgramName;

    @FXML
    private Label lblProgramID;

    @FXML
    private TableView<TherapyProgramTM> tblTherapistProgramManagement;

    @FXML
    private TextField textDuration;

    @FXML
    private TextField textName;

    @FXML
    private AnchorPane therapyProgramPage;

    @FXML
    private TextField txtFee;

    private final TherapyProgramBO therapyProgramBO = new TherapyProgramBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadAllTherapyPrograms();
        generateNewID();
    }

    private void generateNewID() {
        lblProgramID.setText(therapyProgramBO.getNextTherapyProgramId());
    }

    private void loadAllTherapyPrograms() {
        try {
            ArrayList<TherapyProgramDTO> therapyProgramDTOS = therapyProgramBO.loadAllTherapyPrograms();
            ObservableList<TherapyProgramTM> therapyProgramTMSList = FXCollections.observableArrayList();

            for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {
                TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee()
                );
                therapyProgramTMSList.add(therapyProgramTM);
            }
            tblTherapistProgramManagement.setItems(therapyProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapy programs!", Alert.AlertType.ERROR);
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
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        TherapyProgramTM selectedItem = tblTherapistProgramManagement.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error", "Please select a therapy program to delete!", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapy program?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String programId = selectedItem.getProgramId();
            try {
                boolean isDeleted = therapyProgramBO.deleteTherapyProgram(programId);
                if (isDeleted) {
                    showAlert("Success", "Therapy program deleted successfully!", Alert.AlertType.INFORMATION);
                    loadAllTherapyPrograms();
                    clearFields();
                } else {
                    showAlert("Error", "Failed to delete therapy program!", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("Error", "Failed to delete therapy program!", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String programId = lblProgramID.getText();
        String programName = textName.getText();
        int duration = Integer.parseInt(textDuration.getText());
        double fee = Double.parseDouble(txtFee.getText());

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(programId, programName, duration, fee);

        try {
            boolean isSaved = therapyProgramBO.saveTherapyProgram(therapyProgramDTO);
            if (isSaved) {
                showAlert("Success", "Therapy program saved successfully!", Alert.AlertType.INFORMATION);
                loadAllTherapyPrograms();
                clearFields();
            } else {
                showAlert("Error", "Failed to save therapy program!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to save therapy program!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String programId = lblProgramID.getText();
        String programName = textName.getText();
        int duration = Integer.parseInt(textDuration.getText());
        double fee = Double.parseDouble(txtFee.getText());

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(programId, programName, duration, fee);

        try {
            boolean isUpdated = therapyProgramBO.updateTherapyProgram(therapyProgramDTO);
            if (isUpdated) {
                showAlert("Success", "Therapy program updated successfully!", Alert.AlertType.INFORMATION);
                loadAllTherapyPrograms();
                clearFields();
            } else {
                showAlert("Error", "Failed to update therapy program!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to update therapy program!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void tblTherapistProgramManagementOnMouseClicked(MouseEvent event) {
        TherapyProgramTM selectedItem = tblTherapistProgramManagement.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblProgramID.setText(selectedItem.getProgramId());
            textName.setText(selectedItem.getProgramName());
            textDuration.setText(String.valueOf(selectedItem.getDuration()));
            txtFee.setText(String.valueOf(selectedItem.getFee()));
        }
    }

    private void clearFields() {
        textName.clear();
        textDuration.clear();
        txtFee.clear();
        generateNewID();
    }
}
