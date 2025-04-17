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

public class SignUpPageController {

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox<Role> comboxRole;

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

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

    @FXML
    public void initialize() {
        comboxRole.getItems().addAll(Role.values());
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String userName = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = psPassword.getText().trim();
        String selectedRole = String.valueOf((Role) comboxRole.getValue());

        if (name.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty() || selectedRole == null) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (!isValidEmail(txtEmail.getText())) {
            showAlert("Error", "Please enter a valid email address!", Alert.AlertType.ERROR);
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
        // Example: print selected role
        Object selectedRole = comboxRole.getValue();
        System.out.println("Selected role: " + selectedRole);
    }


    private boolean isValidEmail(String text) {
        return text.matches(EMAIL_PATTERN);
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
