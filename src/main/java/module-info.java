module com.example.smp_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.smp_3 to javafx.fxml;
    exports com.example.smp_3;
}