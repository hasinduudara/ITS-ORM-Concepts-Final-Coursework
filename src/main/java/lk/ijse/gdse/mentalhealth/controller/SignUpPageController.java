package lk.ijse.gdse.mentalhealth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.mentalhealth.bo.custom.UserBo;
import lk.ijse.gdse.mentalhealth.util.Role;
import lk.ijse.gdse.mentalhealth.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignUpPageController implements Initializable {

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox<String> comboxRole;

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

    private final UserBo userBo = new UserBOImpl();

    public void initialize (URL url, ResourceBundle resourceBundle) {
        // Initialize the ComboBox with roles
        comboxRole.getItems().addAll(
                "Admin",
                "Receptionist"
        );
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String userName = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = psPassword.getText().trim();
        String selectedRole = comboxRole.getValue();

        if (name.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty() || selectedRole == null) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(psPassword.getText().trim())) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        // Convert string to Role Enum
        Role role = Role.valueOf(selectedRole);

        // Save user to database
        boolean success = userBo.registerUser(name, userName, email, password, role);

        if (success) {
            showAlert("Success", "Account created successfully!", Alert.AlertType.INFORMATION);
            loadUI("/view/LoginPage.fxml");
        } else {
            showAlert("Error", "Failed to create account!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void comboxRoleOnAction(ActionEvent event) {
        // Handle role selection if needed
        String selectedRole = comboxRole.getValue();
        if (selectedRole != null) {
            System.out.println("Selected Role: " + selectedRole);
        }
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
