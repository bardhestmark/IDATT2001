module ntnu.idatt2001.baardshe {
    requires javafx.controls;
    requires javafx.fxml;

    opens idatt2001.baardshe.patientregistry to javafx.fxml;
    exports idatt2001.baardshe.patientregistry;
    exports idatt2001.baardshe.patientregistry.patient;
    opens idatt2001.baardshe.patientregistry.patient to javafx.fxml;
    exports idatt2001.baardshe.patientregistry.fxcomponents;
    opens idatt2001.baardshe.patientregistry.fxcomponents to javafx.fxml;
}