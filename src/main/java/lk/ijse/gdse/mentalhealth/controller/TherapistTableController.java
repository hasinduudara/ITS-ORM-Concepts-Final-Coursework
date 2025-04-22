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
import lk.ijse.gdse.mentalhealth.bo.custom.TherapistBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapistBOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapistDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.TherapistTableTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapistTableController implements Initializable {
    private final TherapistBO therapistBO = new TherapistBOImpl();

    @FXML
    private TableColumn<TherapistTableTM, String> colTherapistAvailability;

    @FXML
    private TableColumn<TherapistTableTM, String> colTherapistID;

    @FXML
    private TableColumn<TherapistTableTM, String> colTherapistName;

    @FXML
    private TableColumn<TherapistTableTM, String> colTherapistSpecialty;

    @FXML
    private TableView<TherapistTableTM> tblMiniTherapist;

    @FXML
    private AnchorPane therapistTablePage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTherapistID.setCellValueFactory(new PropertyValueFactory<>("therapistID"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colTherapistSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colTherapistAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data into table
        loadTherapists();
    }

    private void loadTherapists() {
        try {
            ArrayList<TherapistDTO> therapists = therapistBO.loadAllTherapists();
            ObservableList<TherapistTableTM> therapistTMList = FXCollections.observableArrayList();

            for (TherapistDTO therapistDTO : therapists) {
                TherapistTableTM therapistTM = new TherapistTableTM(
                        therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getAvailability()
                );

                therapistTMList.add(therapistTM);
            }
            tblMiniTherapist.setItems(therapistTMList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void tblMiniTherapistOnMouseClicked(MouseEvent event) {
        TherapistTableTM selectedItem = tblMiniTherapist.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a therapist!", Alert.AlertType.WARNING);
        } else {
            TherapySessionController.getInstance().setTherapistId(selectedItem.getTherapistID());
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
