package AddressRegistry.gui.dialog;

import AddressRegistry.Postal.Address;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class EditCommuneDialog implements Editable {

    private boolean wasEdited = false;

    /**
     * Summons a window to let the user edit a Commune
     *
     * @param address The address to edit
     * @return if edited
     */
    @Override
    public boolean edit(Address address) {
        display(address);
        return wasEdited;
    }

    private void display(Address address) {
        AddressDialogBox addressDialogBox = new AddressDialogBox();

        addressDialogBox.setTitle("Edit commune");
        Label label = new Label("Commune:");
        TextField input = new TextField(address.getCommune().getName());

        //Grid for components:
        GridPane grid = addressDialogBox.getGrid();
        grid.add(label, 0, 0);
        grid.add(input, 1, 0);

        //set the main checks
        Button confirmButton = addressDialogBox.getConfirmButton();
        confirmButton.setOnAction(event -> {
            try {
                if (!address.getCommune().getName().equals(input.getText())) {
                    address.getCommune().setName(input.getText());
                    wasEdited = true;
                }
                addressDialogBox.close();
            } catch (IllegalArgumentException e) {
                addressDialogBox.setError(e.getMessage());
            }
        });

        input.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });

        addressDialogBox.show();
    }
}
