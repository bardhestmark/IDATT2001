package AddressRegistry.gui;

import AddressRegistry.FileReader;
import AddressRegistry.Postal.*;
import AddressRegistry.gui.dialog.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;

/**
 * Class for controlling the primary view of the application
 */
public class PrimaryController {

    private AddressRegistry addressRegistry;
    private List<Address> currentAddressList;
    private ObservableList<Address> observableAddressList;

    private int currentSearch;
    private Comparator<Address> comparator;
    private final ToggleGroup searchToggleGroup = new ToggleGroup();
    private AddEditAddressDialog addEditAddressDialog = new AddEditAddressDialog();

    @FXML
    private MenuBar menuBar;

    //The Table View:
    @FXML
    private TableView<Address> addressTableView;
    @FXML
    private TableColumn<Address, String> postalCodeColumn;
    @FXML
    private TableColumn<Address, String> postalPlaceColumn;
    @FXML
    private TableColumn<Address, String> communeColumn;
    @FXML
    private TableColumn<Address, String> postalCodeCategoryColumn;

    //left side components
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> sortingComboBox;
    @FXML
    private HBox alertBox;
    @FXML
    private RadioButton postalCodeRadio;
    @FXML
    private RadioButton placeRadio;
    @FXML
    private RadioButton communeRadio;

    /**
     * Will be called when the fxml is loaded setting up the necessary components
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        setupMenuBar();
        setupTableView();

        currentSearch = 0;
        comparator = AddressComparators.sortByPostalCode;

        this.addressRegistry = FileReader.readFromFile("post.txt");

        this.currentAddressList = addressRegistry.getAddressList();
        this.observableAddressList = FXCollections.observableArrayList();
        updateObservableList();
        this.addressTableView.setItems(this.observableAddressList);

        setupSortingComboBox();
        setupSearchToggleGroup();
    }

    /**
     * Readies the table view by assigning the correct properties of {@link Address} to their respective column
     * Also sets all potential rows to handle double click events to trigger edit
     */
    private void setupTableView() {
        postalCodeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPostalCode().getCodeAsString()));
        postalPlaceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlace().getName()));
        communeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCommune().getName()));
        postalCodeCategoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPostalCode().getPostalCategory())));

        addressTableView.setRowFactory(t -> {
            TableRow<Address> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (!row.isEmpty())) { //double click triggers edit
                    triggerEditAddress();
                }
            });
            return row;
        });
    }

    /**
     * Readies the menuBar by creating menuitems with actions and adding them to the menubar
     */
    private void setupMenuBar() {
        MenuItem importFromFile = new MenuItem("Import from file");
        MenuItem exportToFile = new MenuItem("Export to file");
        MenuItem exportCurrentSearchToFile = new MenuItem("Export current search to file");
        MenuItem exit = new MenuItem("Exit");
        importFromFile.setOnAction(event -> importFromFile());
        exportToFile.setOnAction(event -> exportToFile(addressRegistry.getAddressList()));
        exportCurrentSearchToFile.setOnAction(event -> exportToFile(currentAddressList));
        exit.setOnAction(event -> System.exit(0));
        menuBar.getMenus().get(0).getItems().addAll(importFromFile, exportToFile, exportCurrentSearchToFile, exit);

        MenuItem editCommune = new MenuItem("Edit commune");
        MenuItem editPlace = new MenuItem("Edit place");
        MenuItem editAddress = new MenuItem("Edit address/zip");
        MenuItem deleteAddress = new MenuItem("Delete address/zip");
        editCommune.setOnAction(event -> editCommune());
        editPlace.setOnAction(event -> editPlace());
        editAddress.setOnAction(event -> triggerEditAddress());
        deleteAddress.setOnAction(event -> triggerDeleteAddress());

        menuBar.getMenus().get(1).getItems().addAll(editCommune, editPlace, editAddress, deleteAddress);

        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> displayAboutDialog());
        menuBar.getMenus().get(2).getItems().add(about);
    }

    /**
     * Sets up the sorting combo box by assigning sorting methods
     */
    private void setupSortingComboBox() {
        sortingComboBox.getItems().add("Sort by code");
        sortingComboBox.getItems().add("Sort by place");
        sortingComboBox.getItems().add("Sort by commune");
        sortingComboBox.getSelectionModel().select(0);
    }

    /**
     * Sets up the search toggle group by adding all search toggles
     * so that only one can be chosen at a time and assigns functionality
     */
    private void setupSearchToggleGroup() {
        postalCodeRadio.setToggleGroup(searchToggleGroup);
        placeRadio.setToggleGroup(searchToggleGroup);
        communeRadio.setToggleGroup(searchToggleGroup);
        searchToggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (searchToggleGroup.getSelectedToggle() != null) {
                RadioButton selectedRadio = (RadioButton) searchToggleGroup.getSelectedToggle();
                if (selectedRadio == postalCodeRadio) currentSearch = 0;
                else if (selectedRadio == placeRadio) currentSearch = 1;
                else if (selectedRadio == communeRadio) currentSearch = 2;
                searchField.requestFocus();
                doSearch();
            }
        });
    }

    /**
     * Imports a chosen .txt file and sets the returned {@link AddressRegistry}
     */
    private void importFromFile() {
        try {
            addressRegistry = FileReader.importFromFile();
            doSearch();
        } catch (IOException e) {
            displayFeedback("Failed to import from file", false);
        } catch (CancellationException ignored) {
        }
    }

    /**
     * Exports the current registry to file
     */
    private void exportToFile(List<Address> addresses) {
        try {
            FileReader.exportToFile(addresses);
        } catch (IOException e) {
            displayFeedback("Failed to export to file", false);
        } catch (CancellationException ignored) {
        }
    }

    /**
     * Method to edit the {@link Commune} connected to the currently selected {@link Address}
     * Will edit commune object so that all connected addresses will be affected
     */
    private void editCommune() {
        try {
            EditCommuneDialog editCommuneDialog = new EditCommuneDialog();
            editCommuneDialog.edit(getSelectedAddress());
        } catch (NullPointerException e) {
            displayFeedback(e.getMessage(), false);
        }
        updateObservableList();
    }

    /**
     * Method to edit the {@link Place} connected to the currently selected {@link Address}
     * Will edit place object so that all connected addresses will be affected
     */
    private void editPlace() {
        try {
            EditPlaceDialog editPlaceDialog = new EditPlaceDialog();
            editPlaceDialog.edit(getSelectedAddress());
        } catch (NullPointerException e) {
            displayFeedback(e.getMessage(), false);
        }
        updateObservableList();
    }

    /**
     * Shows a small window with information about the application
     */
    private void displayAboutDialog() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("About");
        infoAlert.setHeaderText("A postal registry");
        infoAlert.setContentText("Created by\nBård Hestmark");
        infoAlert.showAndWait();
    }

    /**
     * Performs an address search given arguments and the currently chosen search method
     *
     * @param search the search arguments
     */
    private void search(String search) {
        switch (currentSearch) {
            case 0:
                currentAddressList = AddressSearch.searchByCode(addressRegistry.getAddressList(), search);
                break;
            case 1:
                currentAddressList = AddressSearch.searchByPlace(addressRegistry.getAddressList(), search);
                break;
            case 2:
                currentAddressList = AddressSearch.searchByCommune(addressRegistry.getAddressList(), search);
                break;
        }
        updateObservableList();
    }


    /**
     * Called to update view when current list of Addresses change
     */
    private void updateObservableList() {
        currentAddressList.sort(comparator);
        observableAddressList.setAll(currentAddressList);
    }


    /**
     * Displays feedback in the from of a banner with animation on the left side
     *
     * @param message The message to display
     * @param success Positive or negative message
     */
    private void displayFeedback(String message, boolean success) {
        FeedbackAlert.display(alertBox, message, success);
    }

    /**
     * Method to get the currently selected {@link Address} in addresstableview
     *
     * @return the selected {@link Address}
     */
    private Address getSelectedAddress() {
        Address address = addressTableView.selectionModelProperty().get().getSelectedItem();
        if (address == null) throw new NullPointerException("No address selected");
        return address;
    }


    /**
     * Method to search given arguments in the search field
     */
    @FXML
    public void doSearch() {
        search(searchField.getText());
    }

    /**
     * Triggers a dropdown combobox for letting the user sort by code, place or commune
     */
    @FXML
    public void changeSorting() {
        String value = sortingComboBox.getValue();
        switch (value) {
            case "Sort by code":
                comparator = AddressComparators.sortByPostalCode;
                break;
            case "Sort by place":
                comparator = AddressComparators.sortByPlace;
                break;
            case "Sort by commune":
                comparator = AddressComparators.sortByCommune;
                break;
        }
        updateObservableList();
    }

    /**
     * Triggers a dialog to let the user add a new address
     */
    @FXML
    public void triggerAddAddress() {
        if (addressRegistry.addAddress(addEditAddressDialog.add())) {
            displayFeedback("Address was added", true);
            doSearch();
        } else displayFeedback("Address was not added", false);
    }

    /**
     * Triggers a dialog to let the user edit selected address
     */
    @FXML
    public void triggerEditAddress() {
        try {
            if (addEditAddressDialog.edit(getSelectedAddress())) {
                displayFeedback("Address was edited successfully", true);
                doSearch();
            }
        } catch (NullPointerException e) {
            displayFeedback(e.getMessage(), false);
        }
    }

    /**
     * Triggers a dialog to let the user delete selected address
     */
    @FXML
    public void triggerDeleteAddress() {
        if (addressTableView.selectionModelProperty().get().getSelectedItem() == null) {
            displayFeedback("No address selected", false);
            return;
        }
        Address address = addressTableView.selectionModelProperty().get().getSelectedItem();
        if (address == null) return;

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete address");
        confirmDialog.setHeaderText("Delete confirmation");
        confirmDialog.setContentText("Are you sure you want to delete this address?\n"
                + address.getPostalCode().getCodeAsString()
                + ", " + address.getPlace().getName() + ", " + address.getCommune().getName());

        Optional<ButtonType> response = confirmDialog.showAndWait();

        if (response.get() == ButtonType.OK) {
            if (addressRegistry.removeAddress(address)) {
                displayFeedback("Address removed", true);
            } else displayFeedback("Failed to remove address", false); //this shouldn't happen, but just in case
        }
        doSearch();
    }
}