package lk.ijse.gdse.mentalhealth.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.TherapySessionBO;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.gdse.mentalhealth.dto.TherapySessionDTO;
import lk.ijse.gdse.mentalhealth.view.tdm.TherapySessionTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapySessionTableController implements Initializable {

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

    private final TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        allTherapySessionPage.getChildren().clear();
        allTherapySessionPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TherapySession.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSessionID.setCellValueFactory(new PropertyValueFactory<>("sessionID"));
        colPatientID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        colTherapistID.setCellValueFactory(new PropertyValueFactory<>("therapistID"));
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("programID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadSession();
    }

    private void loadSession() {
        ArrayList<TherapySessionDTO> sessions = therapySessionBO.loadAllSessions();
        ObservableList<TherapySessionTM> sessionTMS = FXCollections.observableArrayList();

        for (TherapySessionDTO session : sessions) {
            TherapySessionTM sessionTM = new TherapySessionTM(
                    session.getSessionId(),
                    session.getPatientId(),
                    session.getTherapistId(),
                    session.getProgramId(),
                    session.getSessionDate(),
                    session.getSessionTime(),
                    session.getStatus()
            );
            sessionTMS.add(sessionTM);
        }
        tblAllTherapySession.setItems(sessionTMS);
    }
}
