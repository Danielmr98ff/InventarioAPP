package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.models.Equipo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtNombre;
    @FXML
    private ChoiceBox<String> choiceCategoria;
    @FXML
    private CheckBox chkDisponible;
    @FXML
    private Spinner<Integer> spinnerId;
    @FXML
    private TableColumn<Equipo,String> cDisponibilidad;
    @FXML
    private TableView<Equipo> tabla;
    @FXML
    private TableColumn<Equipo,String> cCategoria;
    @FXML
    private TableColumn<Equipo,String> cNombre;
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

        ObservableList<String> categorias = FXCollections.observableArrayList();
        choiceCategoria.setItems(categorias);

        categorias.add("Keyboard");
        categorias.add("Laptop");

        choiceCategoria.getItems().add("Gamepad");
        choiceCategoria.getItems().add("Monitor");
        choiceCategoria.getItems().add("Computer");
        choiceCategoria.getItems().add("Printer");

        choiceCategoria.getSelectionModel().selectFirst();

        /* Inicialización de la tabla */
        cId.setCellValueFactory( (row)->{
            Equipo equipo = row.getValue();
            Long  id = equipo.getId();
            return new SimpleStringProperty(String.valueOf(id));
        });
        cNombre.setCellValueFactory( (row)->{
            if(row.getValue().getNombre().isBlank()){
                return new SimpleStringProperty("--");
            } else  {
                return new SimpleStringProperty(String.valueOf(row.getValue().getNombre()));
            }
        });
        cCategoria.setCellValueFactory( (row)->{
            return new SimpleStringProperty(String.valueOf(row.getValue().getCategoria()).toUpperCase());
        });
        cDisponibilidad.setCellValueFactory( (row)->{
            var disponibilidad = row.getValue().getDisponible();
            String salida = disponibilidad? "disponible":"no disponible";
            return new SimpleStringProperty(salida);
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
    public void guardar(ActionEvent actionEvent) {
        if (hayCamposVacios()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardar");
        alert.setHeaderText("Guardando");

        String nombre = txtNombre.getText();
        String categoria = choiceCategoria.getSelectionModel().getSelectedItem();
        String disponible = chkDisponible.isSelected() ? "disponible" : "no disponible";

        alert.setContentText(nombre + " | " + categoria + " | " + disponible);
        alert.showAndWait();
        txtNombre.clear();

        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setCategoria(categoria);
        equipo.setId(Long.valueOf(spinnerId.getValue()));
        equipo.setDisponible(chkDisponible.isSelected());

        tabla.getItems().add(equipo);

        tabla.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue+" | "+newValue);
        });

    }

    /**
     * Comprueba si alguno de los campos del formulario está vacío.
     *
     * @return {@code true} si al menos uno de los campos id, nombre, categoría
     *         o disponible está vacío; {@code false} en caso contrario
     */
    private boolean hayCamposVacios() {
        return txtNombre.getText().isEmpty();
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
    public void volver(ActionEvent actionEvent) {
        System.exit(0);
    }
}
