package cesur.dam.inventarioappg1.controllers;

import cesur.dam.inventarioappg1.models.Equipo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtName;
    @FXML
    private ChoiceBox<String> choiceCategory;
    @FXML
    private CheckBox chkAvailable;
    @FXML
    private Spinner<Integer> spinnerId;
    @FXML
    private TableColumn<Equipo,String> cAvailability;
    @FXML
    private TableView<Equipo> table;
    @FXML
    private TableColumn<Equipo,String> cCategory;
    @FXML
    private TableColumn<Equipo,String> cName;
    @FXML
    private TableColumn<Equipo,String> cId;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinnerId.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0,1));

        /*
            ChoiceBox  \
            ListView    \ Observable .getItems() .setItems()
            TableView   /

         */

        ObservableList<String> categories = FXCollections.observableArrayList();
        choiceCategory.setItems(categories);

        categories.add("Keyboard");
        categories.add("Laptop");

        choiceCategory.getItems().add("Gamepad");
        choiceCategory.getItems().add("Monitor");
        choiceCategory.getItems().add("Computer");
        choiceCategory.getItems().add("Printer");

        choiceCategory.getSelectionModel().selectFirst();

        /* Inicialización de la tabla */
        cId.setCellValueFactory( (row)->{
            Equipo equipment = row.getValue();
            Long  id = equipment.getId();
            return new SimpleStringProperty(String.valueOf(id));
        });
        cName.setCellValueFactory( (row)->{
            if(row.getValue().getNombre().isBlank()){
                return new SimpleStringProperty("--");
            } else  {
                return new SimpleStringProperty(String.valueOf(row.getValue().getNombre()));
            }
        });
        cCategory.setCellValueFactory( (row)->{
            return new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase());
        });
        cAvailability.setCellValueFactory( (row)->{
            var availability = row.getValue().getDisponible();
            String output = availability? "disponible":"no disponible";
            return new SimpleStringProperty(output);
        });
    }

    /**
     * Guarda los datos introducidos en el formulario.
     * <p>
     * Comprueba que los campos de id, nombre, categoría y disponible no estén vacíos.
     * Si todos tienen contenido, muestra una alerta informativa con el nombre del
     * artículo, limpia los campos y deja el formulario listo para un nuevo registro.
     * Si algún campo está vacío, no realiza ninguna acción.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón guardar
     */
    @FXML
    public void save(ActionEvent actionEvent) {
        if (hasEmptyFields()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardar");
        alert.setHeaderText("Guardando");

        String name = txtName.getText();
        String category = choiceCategory.getSelectionModel().getSelectedItem();
        String available = chkAvailable.isSelected() ? "disponible" : "no disponible";

        alert.setContentText(name + " | " + category + " | " + available);
        alert.showAndWait();
        txtName.clear();

        Equipo equipment = new Equipo();
        equipment.setNombre(name);
        equipment.setCategoria(category);
        equipment.setId(Long.valueOf(spinnerId.getValue()));
        equipment.setDisponible(chkAvailable.isSelected());

        table.getItems().add(equipment);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue+" | "+newValue);
        });

    }

    /**
     * Comprueba si alguno de los campos del formulario está vacío.
     *
     * @return {@code true} si al menos uno de los campos id, nombre, categoría
     *         o disponible está vacío; {@code false} en caso contrario
     */
    private boolean hasEmptyFields() {
        return txtName.getText().isEmpty();
    }

    /**
     * Cierra la aplicación.
     * <p>
     * Se invoca al pulsar el botón volver y finaliza el proceso con
     * {@link System#exit(int)} usando el código de salida {@code 0}.
     * </p>
     *
     * @param actionEvent evento de acción generado al pulsar el botón volver
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        System.exit(0);
    }
}
