module org.example.inventarioappg1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires mysql.connector.j;
    requires static lombok;

    opens cesur.dam.inventarioappg1 to javafx.fxml;
    opens cesur.dam.inventarioappg1.controllers to javafx.fxml;

    exports cesur.dam.inventarioappg1;
}
