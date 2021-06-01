package idatt2001.baardshe.patientregistry.fxcomponents;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;


/**
 * Factory class that creates javafx node objects
 */
public class FXNodeFactory {

    public static Node getNode(String nodeType) {
        if (nodeType.isBlank()) {
            return null;
        } else if (nodeType.equalsIgnoreCase("tableview")) {
            return new TableView<>();
        } else if (nodeType.equalsIgnoreCase("menubar")) {
            return new MenuBar();
        } else if (nodeType.equalsIgnoreCase("gridpane")) {
            return new GridPane();
        } else if (nodeType.equalsIgnoreCase("textfield")) {
            return new TextField();
        } else if (nodeType.equalsIgnoreCase("textarea")) {
            return new TextArea();
        } else if (nodeType.equalsIgnoreCase("vbox")) {
            return new VBox();
        } else if (nodeType.equalsIgnoreCase("hbox")) {
            return new HBox();
        } else if (nodeType.equalsIgnoreCase("button")) {
            return new Button();
        } else if (nodeType.equalsIgnoreCase("label")) {
            return new Label();
        }
        return null;
    }


}
