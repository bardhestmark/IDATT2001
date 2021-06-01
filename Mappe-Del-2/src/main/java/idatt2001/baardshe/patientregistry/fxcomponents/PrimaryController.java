package idatt2001.baardshe.patientregistry.fxcomponents;

import idatt2001.baardshe.patientregistry.FileHandler;
import idatt2001.baardshe.patientregistry.patient.PatientRegistry;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import idatt2001.baardshe.patientregistry.patient.Patient;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CancellationException;

/**
 * Class for controlling the primary view of the application
 */
public class PrimaryController {

    //Some lists
    private PatientRegistry patientRegistry;
    private ObservableList<Patient> observablePatientList;

    @FXML
    private MenuBar menuBar;

    //The Table View:
    @FXML
    private TableView<Patient> patientTableView;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> ssnColumn;
    @FXML
    private TableColumn<Patient, String> generalPractitionerColumn; //it makes sense to be able to see general practitioner

    //diagnosis could be to big to be viewable in tableview, therefore left out, it is viewable in edit view

    @FXML
    private HBox statusAlertBox; //invisible (height = 0) until 'called upon'

    /**
     * Will be called when the fxml is loaded, sets up the necessary components.
     */
    public void initialize() {
        setupMenuBar();
        setupTableView();

        this.patientRegistry = new PatientRegistry();
        this.observablePatientList = FXCollections.observableArrayList(patientRegistry.getPatients());
        this.patientTableView.setItems(this.observablePatientList);
    }

    public PatientRegistry getPatientRegistry() {
        return patientRegistry;
    }

    /**
     * Readies the table view by assigning the correct properties of {@link Patient} to their respective column
     * Also sets all potential rows to handle double click events to trigger edit
     */
    private void setupTableView() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        generalPractitionerColumn.setCellValueFactory(new PropertyValueFactory<>("generalPractitioner"));

        patientTableView.setRowFactory(t -> {
            TableRow<Patient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) { //double click triggers edit
                    editPatientDialog();
                }
            });
            return row;
        });
    }

    /**
     * Readies the menuBar by creating menuitems with actions and adding them to the menubar
     */
    private void setupMenuBar() {
        MenuItem importCSV = new MenuItem("Import from CSV");
        MenuItem exportCSV = new MenuItem("Export to CSV");
        MenuItem exit = new MenuItem("Exit");
        importCSV.setOnAction(event -> importCSV());
        exportCSV.setOnAction(event -> exportCSV());
        exit.setOnAction(event -> System.exit(0)); //this button could also save the application
        menuBar.getMenus().get(0).getItems().addAll(importCSV, exportCSV, new SeparatorMenuItem(), exit);

        MenuItem addPatient = new MenuItem("Add new patient");
        MenuItem editPatient = new MenuItem("Edit selected patient");
        MenuItem removePatient = new MenuItem("Remove selected patient");
        addPatient.setOnAction(event -> addPatientDialog());
        editPatient.setOnAction(event -> editPatientDialog());
        removePatient.setOnAction(event -> removePatientDialog());
        menuBar.getMenus().get(1).getItems().addAll(addPatient, editPatient, removePatient);

        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> displayAboutDialog());
        menuBar.getMenus().get(2).getItems().add(about);
    }

    /**
     * Called to update the view when the patient registry is changed
     */
    private void updateObservableList() {
        this.observablePatientList.setAll(this.patientRegistry.getPatients());
    }

    /**
     * Imports a chosen .csv file and adds its content to the patientRegistry
     */
    private void importCSV() {
        try {
            FileHandler.importFromCSV(patientRegistry);
            updateObservableList();
            displayFeedback("Successfully read from file"); //this will also trigger if the file is empty, but it's technically not wrong
        } catch (IOException e) {
            displayFeedback("Had some trouble reading file");
        } catch (CancellationException ignored) {
        }
    }

    /**
     * Saves the current patient registry to a chosen or created .csv file
     */
    private void exportCSV() {
        try {
            FileHandler.exportToCSV(patientRegistry);
            displayFeedback("Write to file successful");
        } catch (IOException e) {
            displayFeedback("Had some trouble writing to file");
        } catch (CancellationException ignored) {
        }
    }

    /**
     * Triggers {@link AddEditPatientDialog} to let the user add a new patient
     */
    private void addPatientDialog() {
        if (patientRegistry.addPatient(AddEditPatientDialog.addPatientDialog())) {
            displayFeedback("Patient was added");
            updateObservableList();
        } else {
            displayFeedback("Did not add patient"); //maybe more feedback here?
        }
    }

    /**
     * Triggers {@link AddEditPatientDialog} to let the user edit a patient
     */
    private void editPatientDialog() {
        if (patientTableView.selectionModelProperty().get().getSelectedItem() == null) {
            displayFeedback("No patient selected");
            return;
        }
        if (AddEditPatientDialog.editPatientDialog(patientTableView.selectionModelProperty().get().getSelectedItem())) {
            displayFeedback("Patient was edited");
            updateObservableList();
        }
    }

    /**
     * Triggers a dialog to let the user delete selected user
     */
    private void removePatientDialog() {
        if (patientTableView.selectionModelProperty().get().getSelectedItem() == null) {
            displayFeedback("No patient selected");
            return;
        }
        Patient patient = patientTableView.selectionModelProperty().get().getSelectedItem();
        if (patient == null) return;

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete patient");
        confirmDialog.setHeaderText("Delete confirmation");
        confirmDialog.setContentText("Are you sure you want to delete " + patient.getFirstName() + " " + patient.getLastName() + "?");

        Optional<ButtonType> response = confirmDialog.showAndWait();

        if (response.get() == ButtonType.OK) {
            if (patientRegistry.removePatient(patient)) {
                displayFeedback("Patient removed");
                updateObservableList();
            }
        }
    }

    /**
     * Triggers a banner on the bottom of a window with text to show feedback on different actions
     *
     * @param message The message to display
     */
    public void displayFeedback(String message) {
        KeyValue kv1 = new KeyValue(statusAlertBox.prefHeightProperty(), 20);
        KeyFrame kf1 = new KeyFrame(Duration.millis(150), kv1);
        Timeline grow = new Timeline();
        grow.getKeyFrames().add(kf1);
        grow.setOnFinished(event -> {
            statusAlertBox.getChildren().clear(); //in case alert is already showing
            statusAlertBox.getChildren().add(new Label(message));
        });

        PauseTransition pause = new PauseTransition(Duration.millis(2500));
        pause.setOnFinished(event -> statusAlertBox.getChildren().clear());

        KeyValue kv2 = new KeyValue(statusAlertBox.prefHeightProperty(), 0);
        KeyFrame kf2 = new KeyFrame(Duration.millis(150), kv2);
        Timeline shrink = new Timeline();
        shrink.getKeyFrames().add(kf2);

        SequentialTransition sequence = new SequentialTransition(grow, pause, shrink);
        sequence.play();
    }

    /**
     * Triggers a small window that displays information about the application
     */
    private void displayAboutDialog() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("About");
        infoAlert.setHeaderText("Patients registry");
        infoAlert.setContentText("Created by\nBård Hestmark");
        infoAlert.showAndWait();
    }

    /**
     * Triggered by button addPatient
     */
    @FXML
    public void triggerAddPatient() {
        addPatientDialog();
    }

    /**
     * Triggered by button removePatient
     */
    @FXML
    public void triggerRemovePatient() {
        removePatientDialog();
    }


    /**
     * Triggered by button editPatient
     */
    @FXML
    public void triggerEditPatient() {
        editPatientDialog();
    }
}