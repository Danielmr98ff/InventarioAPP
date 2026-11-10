package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.AppG1;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class NavigationController {

    public enum View {
        LOGIN("views/login-view.fxml", "Inventario App G1 - Login"),
        MAIN("views/main-view.fxml", "Inventario App G1 - Inventario"),
        CREATE("views/create-view.fxml", "Inventario App G1 - Nuevo equipo");

        private final String fxml;
        private final String title;

        View(String fxml, String title) {
            this.fxml = fxml;
            this.title = title;
        }

        public String getFxml() {
            return fxml;
        }

        public String getTitle() {
            return title;
        }
    }

    private static NavigationController instance;

    private final Stage stage;

    private NavigationController(Stage stage) {
        this.stage = stage;
    }

    public static NavigationController init(Stage stage) {
        instance = new NavigationController(stage);
        return instance;
    }

    public static NavigationController getInstance() {
        return instance;
    }

    public void go(View view) {
        URL fxml = AppG1.class.getResource(view.getFxml());
        if (fxml == null) {
            throw new IllegalStateException("No se encontró el FXML " + view.getFxml());
        }

        try {
            FXMLLoader loader = new FXMLLoader(fxml);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(view.getTitle());
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista " + view, e);
        }
    }
}
