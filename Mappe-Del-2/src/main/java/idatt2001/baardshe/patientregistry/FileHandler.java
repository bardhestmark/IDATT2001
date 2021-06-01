package idatt2001.baardshe.patientregistry;

import idatt2001.baardshe.patientregistry.patient.Patient;
import idatt2001.baardshe.patientregistry.patient.PatientRegistry;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.CancellationException;

/**
 * Class that handles file IO for the application
 */
public class FileHandler {

    /**
     * Lets the user choose a file to read from
     * Checks and readies the file for the fileReader method
     *
     * @param patientRegistry The registry to fill
     * @throws IOException
     */
    public static void importFromCSV(PatientRegistry patientRegistry) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().normalize().toFile());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setTitle("Open CSV");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file == null) throw new CancellationException();

        readCSVFile(file, patientRegistry);
    }

    /**
     * Opens file and reads data from it
     * Adds data to patientRegistry
     *
     * @param file            The file to read form
     * @param patientRegistry The registry to fill
     * @throws IOException
     */
    private static void readCSVFile(File file, PatientRegistry patientRegistry) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

        String line;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] patientInfo = line.split(";");

            //not reading diagnosis because the provided file didn't

            try {
                patientRegistry.addPatient(new Patient(patientInfo[3], patientInfo[0], patientInfo[1], "", patientInfo[2]));
            } catch (IllegalArgumentException ignored) {
                /*
                this will allow the reader to continue if a patient in the csv is invalid,
                the invalid patient will simply not be added, as they usually should not have been able to be saved to the file in the first place
                */
            }
        }

        bufferedReader.close();
    }

    /**
     * Lets the user choose a file to write to
     * Checks and readies the file for the fileWriter method
     *
     * @param patientRegistry Registry to write to file
     * @throws IOException
     */
    public static void exportToCSV(PatientRegistry patientRegistry) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().normalize().toFile());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setTitle("Export CSV");
        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) throw new CancellationException();

        writeCSVFile(file, patientRegistry);
    }

    /**
     * Opens file and writes data to it
     * Writes data from patientRegistry
     *
     * @param file            The file to write to
     * @param patientRegistry The registry to write
     * @throws IOException
     */
    private static void writeCSVFile(File file, PatientRegistry patientRegistry) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        fileWriter.write("firstName;lastName;generalPractitioner;socialSecurityNumber\n");

        //not saving diagnosis because the provided file didn't

        for (Patient patient : patientRegistry.getPatients()) {
            fileWriter.write(patient.getFirstName() + ";" + patient.getLastName() + ";" + patient.getGeneralPractitioner() + ";" + patient.getSocialSecurityNumber() + "\n");
        }

        fileWriter.close();
    }
}
