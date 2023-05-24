module gillingham.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens gillingham.capstone to javafx.fxml;
    exports gillingham.capstone;
}