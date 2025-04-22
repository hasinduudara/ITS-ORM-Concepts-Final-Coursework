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
import lk.ijse.gdse.mentalhealth.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapyProgramDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.TherapyProgramTableTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapyProgramTableController implements Initializable {

    @FXML
    private TableColumn<TherapyProgramTableTM, Integer> colDuration;

    @FXML
    private TableColumn<TherapyProgramTableTM, String> colProgramID;

    @FXML
    private TableColumn<TherapyProgramTableTM, String> colProgramName;

    @FXML
    private TableColumn<TherapyProgramTableTM, Double> colfee;

    @FXML
    private TableView<TherapyProgramTableTM> tblMiniTherapyProgram;

    @FXML
    private AnchorPane therapyProgramTablePage;

    private final TherapyProgramBO therapyProgramBO = new TherapyProgramBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colfee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadTherapyPrograms();
    }

    private void loadTherapyPrograms() {
        try {
            ArrayList<TherapyProgramDTO> therapyProgramDTOS = therapyProgramBO.loadAllTherapyPrograms();
            ObservableList<TherapyProgramTableTM> therapyProgramTMSList = FXCollections.observableArrayList();

            for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {
                TherapyProgramTableTM therapyProgramTableTM = new TherapyProgramTableTM(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee()
                );
                therapyProgramTMSList.add(therapyProgramTableTM);
            }
            tblMiniTherapyProgram.setItems(therapyProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapy programs", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void tblMiniTherapyProgramOnMouseClicked(MouseEvent event) {
        TherapyProgramTableTM selectedProgram = tblMiniTherapyProgram.getSelectionModel().getSelectedItem();

        if (selectedProgram == null) {
            showAlert("Warning", "Please select a therapy program", Alert.AlertType.WARNING);
        } else {
            TherapySessionController.getInstance().setProgramId(selectedProgram.getProgramId());
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
