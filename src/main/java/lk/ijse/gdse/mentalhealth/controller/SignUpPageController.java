package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class SignUpPageController {

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox<?> comboxRole;

    @FXML
    private Label lblGoToSignIn;

    @FXML
    private PasswordField psPassword;

    @FXML
    private AnchorPane signUpPage;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnSignUpOnAction(ActionEvent event) {

    }

    @FXML
    void comboxRoleOnAction(ActionEvent event) {

    }

    @FXML
    void lblGoToSignInOnAction(MouseEvent event) {
        loadUI("/view/LoginPage.fxml");
    }

    private void loadUI(String resource) {
        signUpPage.getChildren().clear();
        try {
            signUpPage.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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
