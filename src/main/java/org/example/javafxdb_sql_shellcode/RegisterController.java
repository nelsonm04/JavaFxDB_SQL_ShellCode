package org.example.javafxdb_sql_shellcode;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.example.javafxdb_sql_shellcode.db.ConnDbOps;

import java.io.IOException;

public class RegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final ConnDbOps db = new ConnDbOps();

    @FXML
    private void handleRegister() {
        String name = firstNameField.getText() + " " + lastNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();


        db.insertUser(name, email, "", "", password); // phone + address left blank for now

        showAlert("Success", "Account created! Return to login.");

        loadLoginScreen();
    }

    @FXML
    private void handleBack() {
        loadLoginScreen();
    }

    private void loadLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
