package AddressRegistry.gui.dialog;

import AddressRegistry.Postal.Address;
import AddressRegistry.Postal.Commune;
import AddressRegistry.Postal.Place;
import AddressRegistry.Postal.PostalCode;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class AddEditAddressDialog implements Editable, Addable {

    boolean wasEdited = false;
    private Address response;

    private enum Mode {
        ADD, EDIT
    }

    private Mode mode;

    /**
     * Summons a window to let the user edit an address
     *
     * @param address The address to edit
     * @return if edited
     */
    @Override
    public boolean edit(Address address) {
        mode = Mode.EDIT;
        wasEdited = false;
        display(address);
        return wasEdited;
    }

    /**
     * Summons a window to let the user add a new address
     *
     * @return The produced address
     */
    @Override
    public Address add() {
        mode = Mode.ADD;
        display(null);
        return response;
    }

    public void display(Address address) {
        AddressDialogBox addressDialogBox = new AddressDialogBox();

        switch (this.mode) {
            case ADD:
                addressDialogBox.setTitle("Add new address");
            case EDIT:
                addressDialogBox.setTitle("Edit address");
        }

        //Labels
        Label postalCodeLabel = new Label("Postal code:");
        Label postalCategoryLabel = new Label("Postal category letter:");
        Label communeLabel = new Label("Commune:");
        Label placeLabel = new Label("Place:");

        //Input fields
        TextField postalCodeInput = new TextField();
        ComboBox<Character> postalCategoryInput = new ComboBox<>();
        TextField communeInput = new TextField();
        TextField placeInput = new TextField();

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (!newText.matches("-?([0-9][0-9]*)?")) {
                addressDialogBox.getErrorLabel().setText("Only numbers in postal code field");
                return null;
            }
            if (newText.length() > 4) {
                addressDialogBox.getErrorLabel().setText("Number must be between 0 and 9999");
                return null;
            }
            addressDialogBox.getErrorLabel().setText("");
            return change;
        };

        try {
            postalCodeInput.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        } catch (IllegalArgumentException e) {
            addressDialogBox.getErrorLabel().setText(e.getMessage());
        }

        postalCategoryInput.getItems().addAll('B', 'F', 'G', 'P', 'S');
        postalCategoryInput.getSelectionModel().select(0);
        if (mode.equals(Mode.EDIT)) {
            postalCodeInput.setText(String.valueOf(address.getPostalCode().getCode()));
            postalCategoryInput.getSelectionModel().select(address.getPostalCode().getPostalCategory());
            postalCodeInput.setDisable(true);

            for (Character c : postalCategoryInput.getItems()) {
                if (c == address.getPostalCode().getPostalCategory()) {
                    postalCategoryInput.getSelectionModel().select(c);
                    break;
                }
            }
            communeInput.setText(address.getCommune().getName());
            placeInput.setText(address.getPlace().getName());
        }

        GridPane grid = addressDialogBox.getGrid();
        grid.add(postalCodeLabel, 0, 0);
        grid.add(postalCategoryLabel, 0, 1);
        grid.add(communeLabel, 0, 2);
        grid.add(placeLabel, 0, 3);

        grid.add(postalCodeInput, 1, 0);
        grid.add(postalCategoryInput, 1, 1);
        grid.add(communeInput, 1, 2);
        grid.add(placeInput, 1, 3);

        //set the main checks
        Button confirmButton = addressDialogBox.getConfirmButton();
        confirmButton.setOnAction(event -> {
            try {
                if (mode.equals(Mode.ADD)){
                    PostalCode postalCode = new PostalCode(Integer.parseInt(postalCodeInput.getText()), postalCategoryInput.getSelectionModel().getSelectedItem());
                    Commune commune = new Commune(communeInput.getText(), 1);
                    Place place = new Place(placeInput.getText());
                    response = new Address(commune, place, postalCode);
                } else {
                    if (!postalCategoryInput.getSelectionModel().getSelectedItem().equals(address.getPostalCode().getPostalCategory())) {
                        address.getPostalCode().setPostalCategory(postalCategoryInput.getSelectionModel().getSelectedItem());
                        wasEdited = true;
                    }
                    if (!communeInput.getText().equals(address.getCommune().getName())) {
                        address.setCommune(new Commune(communeInput.getText(), 1));
                        wasEdited = true;
                    }
                    if (!placeInput.getText().equals(address.getPlace().getName())) {
                        address.setPlace(new Place(placeInput.getText()));
                        wasEdited = true;
                    }
                }
                addressDialogBox.getWindow().close();
            } catch (IllegalArgumentException e) {
                addressDialogBox.getErrorLabel().setText(e.getMessage());
            }
        });

        //there is probably an easier way to set these...
        postalCodeInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });
        postalCategoryInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });
        communeInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });
        placeInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });

        addressDialogBox.getWindow().showAndWait();
    }
}