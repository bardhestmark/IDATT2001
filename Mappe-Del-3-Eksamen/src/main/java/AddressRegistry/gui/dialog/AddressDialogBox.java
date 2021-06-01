package AddressRegistry.gui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddressDialogBox extends Node {

    private final Stage window = new Stage();
    private final GridPane grid = new GridPane();
    private final Label errorLabel = new Label("");
    private final Button confirmButton = new Button("OK");
    private final Button cancelButton = new Button("Cancel");
    private final HBox buttonBox = new HBox();
    private final VBox layout = new VBox();
    private final Scene scene;

    public AddressDialogBox() {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);

        grid.setHgap(30);
        grid.setVgap(10);

        errorLabel.setTextFill(Paint.valueOf("#D32F2F"));

        cancelButton.setOnAction(event -> window.close());

        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(errorLabel, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);


        layout.setSpacing(10);
        layout.getChildren().addAll(grid, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        scene = new Scene(layout);
        window.setScene(scene);
    }

    public Stage getWindow() {
        return window;
    }

    public GridPane getGrid() {
        return grid;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }

    public VBox getLayout() {
        return layout;
    }

    public void setTitle(String title) {
        window.setTitle(title);
    }

    public void setError(String error) {
        this.errorLabel.setText(error);
    }

    public void show() {
        window.showAndWait();
    }

    public void close() {
        window.close();
    }
}
