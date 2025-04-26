package lk.ijse.gdse.mentalhealth.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReceptionistDashboardController implements Initializable {

    @FXML
    private AnchorPane AnchorPaneReceptionistDashboard;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReporting;

    @FXML
    private Button btnTherapySession;

    @FXML
    private AnchorPane receptionistDashbordPage;

    @FXML
    private Label lblAdminDashboardDate;

    @FXML
    private Label lblAdminDashboardTime;

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        navigateTo("/view/LoginPage.fxml");
    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        loadUI("/view/Patient.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadUI("/view/Payment.fxml");
    }

    @FXML
    void btnReportingOnAction(ActionEvent event) {
//        navigateTo("/view/FinancialReport.fxml");
        loadUI("/view/FinancialReport.fxml");
    }

    @FXML
    void btnTherapySessionOnAction(ActionEvent event) {
        loadUI("/view/TherapySession.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUI("/view/Patient.fxml");
        startClock();
    }

    private void loadUI(String resource) {
        AnchorPaneReceptionistDashboard.getChildren().clear();
        try {
            AnchorPaneReceptionistDashboard.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath) {
        try {
            receptionistDashbordPage.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(receptionistDashbordPage.widthProperty());
            load.prefHeightProperty().bind(receptionistDashbordPage.heightProperty());
            receptionistDashbordPage.getChildren().add(load);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblAdminDashboardTime.setText(LocalTime.now().format(timeFormatter));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            lblAdminDashboardDate.setText(LocalDate.now().format(dateFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

}
