package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.view.tdm.TherapySessionTM;

import java.io.IOException;

public class TherapySessionTableController {

    @FXML
    private AnchorPane allTherapySessionPage;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<TherapySessionTM, String> colDate;

    @FXML
    private TableColumn<TherapySessionTM, String> colPatientID;

    @FXML
    private TableColumn<TherapySessionTM, String> colProgramID;

    @FXML
    private TableColumn<TherapySessionTM, String> colSessionID;

    @FXML
    private TableColumn<TherapySessionTM, String> colStatus;

    @FXML
    private TableColumn<TherapySessionTM, String> colTherapistID;

    @FXML
    private TableColumn<TherapySessionTM, String> colTime;

    @FXML
    private TableView<TherapySessionTM> tblAllTherapySession;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        allTherapySessionPage.getChildren().clear();
        allTherapySessionPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TherapySession.fxml")));
    }

//    private void loadUI(String resource) {
//        allTherapySessionPage.getChildren().clear();
//        try {
//            allTherapySessionPage.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
//        } catch (E)
//    }

}
