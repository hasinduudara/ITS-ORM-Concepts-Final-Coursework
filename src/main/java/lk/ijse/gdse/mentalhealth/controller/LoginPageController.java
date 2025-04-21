package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.AuthService;
import lk.ijse.gdse.mentalhealth.util.Role;

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

    private final AuthService authService = new AuthService(); // Injecting AuthService

    private boolean isPasswordVisible = false;

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        String username = txtLoginUsername.getText().trim();
        String password = psPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password cannot be empty!", Alert.AlertType.ERROR);
            return;
        }

        Role userRole = authService.authenticateUser(username, password);

        if (userRole != null) {
            navigateToDashBoard(userRole);
        } else {
            showAlert("Error", "Invalid username or password!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void lblCreateAccountOnMouseClicked(MouseEvent event) throws IOException {
//        loadUI("/view/SignUpPage.fxml");
        loginPage.getChildren().clear();
        loginPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignUpPage.fxml")));
    }

    @FXML
    void lblForgotPasswordOnMouseClicked(MouseEvent event) {

    }

    private void loadUI(String resource) {
        loginPage.getChildren().clear();
        try {
            loginPage.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            System.out.println("Failed to load UI: " + e.getMessage());
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToDashBoard (Role role) {
        if (role == Role.ADMIN) {
            loadUI("/view/AdminDashboard.fxml");
        } else if (role == Role.RECEPTIONIST) {
//            loadUI("/view/ReceptionistDashboard.fxml");
        } else {
            showAlert("Error", "Unauthorized access!", Alert.AlertType.ERROR);
        }
    }

}
