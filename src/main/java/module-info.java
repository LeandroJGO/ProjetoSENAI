module com.mycompany.dukemarket.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    requires java.sql;

    opens com.mycompany.dukemarket.javafx to javafx.fxml;
    opens com.mycompany.dukemarket.javafx.controller to javafx.fxml;
    
    exports com.mycompany.dukemarket.javafx;
    exports com.mycompany.dukemarket.javafx.controller;
    exports com.mycompany.dukemarket.javafx.DAO;
    exports com.mycompany.dukemarket.javafx.model;
    exports connection;
    
    requires mysql.connector.java;
    
}
