package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class LoginPageController {

    @FXML
    private Button btnSignIn;

    @FXML
    private Label lblCreateAccount;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private AnchorPane loginPage;

    @FXML
    private PasswordField psPassword;

    @FXML
    private TextField txtLoginUsername;



    @FXML
    void btnSignInOnAction(ActionEvent event) {

    }

    @FXML
    void lblCreateAccountOnMouseClicked(MouseEvent event) {
        loadUI("/view/SignUpPage.fxml");
    }

    @FXML
    void lblForgotPasswordOnMouseClicked(MouseEvent event) {

    }

    private void loadUI(String resource) {
        loginPage.getChildren().clear();
        try {
            loginPage.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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

}
