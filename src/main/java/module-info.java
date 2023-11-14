module com.example.whatsup_certifier {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.whatsup_certifier to javafx.fxml;
    exports com.example.whatsup_certifier;
}