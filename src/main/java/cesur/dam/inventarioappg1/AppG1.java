package cesur.dam.inventarioappg1;

import cesur.dam.inventarioappg1.controllers.NavigationController;
import cesur.dam.inventarioappg1.data.DataBaseInitializer;
import cesur.dam.inventarioappg1.data.DataSourceFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.sql.DataSource;

public class AppG1 extends Application {
    @Override
    public void start(Stage stage) {
        // Prepara la base de datos (tabla y datos iniciales) reutilizando la capa DAO
        DataSource ds = DataSourceFactory.getDataSource();
        DataBaseInitializer.initialize(ds);
        DataBaseInitializer.loadData(ds);

        // El controlador de navegación toma el escenario principal y muestra la vista de login
        NavigationController nav = NavigationController.init(stage);
        nav.go(NavigationController.View.LOGIN);
    }
}
