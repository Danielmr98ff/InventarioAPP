package cesur.dam.inventarioappg1.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;

    @FXML
    public void login(ActionEvent actionEvent) {
        if (txtUser.getText().isBlank() || txtPassword.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login");
            alert.setHeaderText("Datos incompletos");
            alert.setContentText("Introduce el usuario y la contraseña para continuar.");
            alert.showAndWait();
            return;
        }

        NavigationController.getInstance().go(NavigationController.View.MAIN);
    }
}
