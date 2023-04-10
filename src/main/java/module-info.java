module gillingham.capstone {
    requires javafx.controls;
    requires javafx.fxml;


    opens gillingham.capstone to javafx.fxml;
    exports gillingham.capstone;
}