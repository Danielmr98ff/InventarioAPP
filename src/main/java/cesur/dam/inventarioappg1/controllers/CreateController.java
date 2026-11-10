package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.data.DAO;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import cesur.dam.inventarioappg1.data.EquipoJdbcDAO;
import cesur.dam.inventarioappg1.models.Equipo;
import cesur.dam.inventarioappg1.ui.helper.MessageHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    private final DAO<Equipo> equipoDAO =
            new EquipoJdbcDAO(DataSourceFactory.getDataSource());

    @FXML
    private TextField txtName;
    @FXML
    private ChoiceBox<String> choiceCategory;
    @FXML
    private CheckBox chkAvailable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> categories = FXCollections.observableArrayList();
        choiceCategory.setItems(categories);

        categories.add("Keyboard");
        categories.add("Laptop");

        choiceCategory.getItems().add("Gamepad");
        choiceCategory.getItems().add("Monitor");
        choiceCategory.getItems().add("Computer");
        choiceCategory.getItems().add("Printer");

        choiceCategory.getSelectionModel().selectFirst();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (hasEmptyFields()) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Guardar");
            warning.setHeaderText("Datos incompletos");
            warning.setContentText("El nombre y la categoría son obligatorios.");
            warning.showAndWait();
            return;
        }

        String name = txtName.getText();
        String category = choiceCategory.getSelectionModel().getSelectedItem();

        Equipo equipment = new Equipo();
        equipment.setNombre(name);
        equipment.setCategoria(category);
        equipment.setDisponible(chkAvailable.isSelected());

        try {
            equipoDAO.save(equipment);
        } catch (RuntimeException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Guardar");
            error.setHeaderText("No se pudo guardar el equipo");
            error.setContentText("Se produjo un error al guardar en la base de datos:\n"
                    + e.getMessage());
            error.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardar");
        alert.setHeaderText("Equipo guardado en la base de datos");
        alert.setContentText("Id " + equipment.getId() + " | " + name + " | "
                + category + " | "
                + (chkAvailable.isSelected() ? "disponible" : "no disponible"));
        alert.showAndWait();

        txtName.clear();

        NavigationController.getInstance().go(NavigationController.View.MAIN);
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        NavigationController.getInstance().go(NavigationController.View.MAIN);
    }

    private boolean hasEmptyFields() {
        String category = choiceCategory.getSelectionModel().getSelectedItem();
        return txtName.getText().isBlank() || category == null || category.isBlank();
    }

    @FXML
    protected void onActionIdAvailable(ActionEvent event) {
        MessageHelper.debug(event, "TODO: onActionIdAvailable");
    }
}
