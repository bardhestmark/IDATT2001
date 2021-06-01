module PostalCodeRegistry {
    requires javafx.controls;
    requires javafx.fxml;

    exports AddressRegistry;
    opens AddressRegistry.Postal;
    exports AddressRegistry.Postal;
    opens AddressRegistry;
    exports AddressRegistry.gui;
    opens AddressRegistry.gui;
    exports AddressRegistry.gui.dialog;
    opens AddressRegistry.gui.dialog;
}