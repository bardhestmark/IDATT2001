package idatt2001.baardshe.patientregistry.fxcomponents;

import idatt2001.baardshe.patientregistry.patient.Patient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class with static methods that show a dialog to add or edit a patient
 */
public class AddEditPatientDialog {

    private static Patient response = null;
    private static boolean wasEdited;

    /**
     * Displays inputs to add patient
     *
     * @return New patient
     */
    public static Patient addPatientDialog() {
        display(null);
        return response;
    }

    /**
     * Displays inputs to edit patient
     *
     * @param patient The patient to edit
     * @return wasEdited To show feedback only if the patient was edited
     */
    public static boolean editPatientDialog(Patient patient) {
        wasEdited = false;
        if (patient != null) display(patient);
        return wasEdited;
    }

    /**
     * Static method to display input for add or edit
     *
     * @param patient If edit; the patient to edit, null if adding patient
     * @return the entered patient
     */
    private static void display(Patient patient) {
        boolean isEdit = patient != null;

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block input events until handled
        if (!isEdit) window.setTitle("Add new patient");
        else window.setTitle("Edit patient");
        window.setMinWidth(350);

        //Labels
        Label firstNameLabel = (Label) FXNodeFactory.getNode("label"); //intellij complains that these might produce nullPointerException, ignored
        Label lastNameLabel = (Label) FXNodeFactory.getNode("label");
        Label socialSecurityNumberLabel = (Label) FXNodeFactory.getNode("label");
        Label generalPractitionerLabel = (Label) FXNodeFactory.getNode("label");
        Label diagnosisLabel = (Label) FXNodeFactory.getNode("label");

        firstNameLabel.setText("First name:");
        lastNameLabel.setText("Last name:");
        socialSecurityNumberLabel.setText("Social security number:");
        generalPractitionerLabel.setText("General practitioner:");
        diagnosisLabel.setText("Diagnosis");

        //Input fields
        TextField firstNameInput = (TextField) FXNodeFactory.getNode("textField");
        TextField lastNameInput = (TextField) FXNodeFactory.getNode("textField");
        TextField socialSecurityNumberInput = (TextField) FXNodeFactory.getNode("textField");
        TextField generalPractitionerInput = (TextField) FXNodeFactory.getNode("textField");
        TextArea diagnosisInput = (TextArea) FXNodeFactory.getNode("textArea");
        diagnosisInput.setPrefWidth(50);
        diagnosisInput.setPrefHeight(50);

        //Set fields if edit
        if (isEdit) {
            firstNameInput.setText(patient.getFirstName());
            lastNameInput.setText(patient.getLastName());
            socialSecurityNumberInput.setText(patient.getSocialSecurityNumber());
            generalPractitionerInput.setText(patient.getGeneralPractitioner());
            diagnosisInput.setText(patient.getDiagnosis());
        }

        //Grid for components:
        GridPane grid = (GridPane) FXNodeFactory.getNode("gridpane");
        grid.setHgap(30);
        grid.setVgap(10);

        grid.add(firstNameLabel, 0, 0);
        grid.add(lastNameLabel, 0, 1);
        grid.add(socialSecurityNumberLabel, 0, 2);
        grid.add(generalPractitionerLabel, 0,3);
        grid.add(diagnosisLabel, 0,4);

        grid.add(firstNameInput, 1, 0);
        grid.add(lastNameInput, 1, 1);
        grid.add(socialSecurityNumberInput, 1, 2);
        grid.add(generalPractitionerInput, 1,3);
        grid.add(diagnosisInput,1,4);

        //A label that can be set in case of invalid arguments etc.
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Paint.valueOf("#D32F2F"));

        //The OK button
        Button confirmButton = (Button) FXNodeFactory.getNode("button");
        confirmButton.setText("OK");

        //set the main checks
        confirmButton.setOnAction(event -> {
            try {
                if (isEdit) {
                    if (!patient.getSocialSecurityNumber().equals(socialSecurityNumberInput.getText())) {
                        patient.setSocialSecurityNumber(socialSecurityNumberInput.getText());
                        wasEdited = true;
                    }
                    if (!patient.getFirstName().equals(firstNameInput.getText())) {
                        patient.setFirstName(firstNameInput.getText());
                        wasEdited = true;
                    }
                    if (!patient.getLastName().equals(lastNameInput.getText())) {
                        patient.setLastName(lastNameInput.getText());
                        wasEdited = true;
                    }
                    if (!patient.getGeneralPractitioner().equals(generalPractitionerInput.getText())) {
                        patient.setGeneralPractitioner(generalPractitionerInput.getText());
                        wasEdited = true;
                    }
                    if (!patient.getDiagnosis().equals(diagnosisInput.getText())) {
                        patient.setDiagnosis(diagnosisInput.getText());
                        wasEdited = true;
                    }
                } else
                    response =
                            new Patient(socialSecurityNumberInput.getText(), firstNameInput.getText(),
                                    lastNameInput.getText(), diagnosisInput.getText(), generalPractitionerInput.getText());
                window.close();
            } catch (IllegalArgumentException e) {
                errorLabel.setText(e.getMessage());
            }
        });

        //there is probably an easier way to set these...
        firstNameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });
        lastNameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });
        socialSecurityNumberInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });
        generalPractitionerInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirmButton.fire();
        });

        //The cancel button
        Button cancelButton = (Button) FXNodeFactory.getNode("button");
        cancelButton.setText("Cancel");

        cancelButton.setOnAction(event -> {
            response = null;
            window.close();
        });

        //Box to align the bottom components
        HBox buttonBox = (HBox) FXNodeFactory.getNode("HBox");
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(errorLabel, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        //Box to align grid an buttonbox
        VBox layout = (VBox) FXNodeFactory.getNode("VBox");
        layout.setSpacing(10);
        layout.getChildren().addAll(grid, buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
