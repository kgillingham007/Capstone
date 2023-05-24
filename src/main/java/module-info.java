module gillingham.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens gillingham.capstone to javafx.fxml;
    exports gillingham.capstone;
    opens gillingham.capstone.controller to javafx.fxml;
    exports gillingham.capstone.controller;
    opens gillingham.capstone.model to javafx.base;
}