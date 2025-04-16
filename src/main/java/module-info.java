module lk.ijse.gdse.mentalhealth {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.gdse.mentalhealth to javafx.fxml;
    exports lk.ijse.gdse.mentalhealth;
}