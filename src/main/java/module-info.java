module lk.ijse.gdse.mentalhealth {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;

    requires java.desktop;
    requires com.jfoenix;
    requires lombok;
    requires jakarta.persistence;
    requires jbcrypt;
    requires java.naming;


    opens lk.ijse.gdse.mentalhealth to javafx.fxml;
    exports lk.ijse.gdse.mentalhealth;
    opens lk.ijse.gdse.mentalhealth.controller to javafx.fxml;

    //    opens lk.ijse.gdse.mentalhealth.view.tdm to javafx.base;
//    opens lk.ijse.gdse.mentalhealth.entity to org.hibernate.orm.core;
    opens lk.ijse.gdse.mentalhealth.config to jakarta.persistence;
}