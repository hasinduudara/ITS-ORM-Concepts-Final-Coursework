package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    private static AdminDashboardController instance;

    @FXML
    private AnchorPane AdminDashboardPage;

    @FXML
    private AnchorPane AnchorPaneAdminDashboard;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReporting;

    @FXML
    private Button btnTherapist;

    @FXML
    private Button btnTherapyProgram;

    @FXML
    private Button btnTherapySession;

    @FXML
    private Label lblAdminDashboardDate;

    @FXML
    private Label lblAdminDashboardTime;

    public AdminDashboardController() {
        instance = this;
    }

    public static AdminDashboardController getInstance() {
        return instance;
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportingOnAction(ActionEvent event) {

    }

    @FXML
    void btnTherapistOnAction(ActionEvent event) {

    }

    @FXML
    void btnTherapyProgramOnAction(ActionEvent event) {

    }

    @FXML
    void btnTherapySessionOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        navigateTo("/view/AdminDashboard.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            AdminDashboardPage.getChildren().clear();
            AdminDashboardPage.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the page!").show();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadUI(String resource) {
        AdminDashboardPage.getChildren().clear();
        try {
            AdminDashboardPage.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

}
