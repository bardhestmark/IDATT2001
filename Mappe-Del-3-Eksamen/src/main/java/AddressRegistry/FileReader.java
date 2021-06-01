package AddressRegistry;

import AddressRegistry.Postal.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CancellationException;

public class FileReader {

    /**
     * Lets the user choose a file to read from
     * Checks and readies the file for the fileReader method
     *
     * @return addressRegistry
     * @throws IOException
     */
    public static AddressRegistry importFromFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().normalize().toFile());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt files (*.txt)", "*.txt"));
        fileChooser.setTitle("Open txt");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file == null) throw new CancellationException();

        return readFile(file);
    }

    /**
     * Reads a given file name
     *
     * @param filename The filename
     * @return addressRegistry
     * @throws IOException
     */
    public static AddressRegistry readFromFile(String filename) throws IOException {
        return readFile(new File(filename));
    }

    /**
     * Opens file and reads data from it
     * Adds data to addressRegistry
     *
     * @param file The file to read form
     * @return filled addressRegistry
     * @throws IOException
     */
    private static AddressRegistry readFile(File file) throws IOException {
        AddressRegistry addressRegistry = new AddressRegistry();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

        String line;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] postalInfo = line.split("\t");
            Commune commune = new Commune(postalInfo[3], Integer.parseInt(postalInfo[2]));
            Place place = new Place(postalInfo[1]);
            PostalCode postalCode = new PostalCode(Integer.parseInt(postalInfo[0]), postalInfo[4].charAt(0));

            Address address = new Address(commune, place, postalCode);
            addressRegistry.addAddress(address);
        }
        bufferedReader.close();
        return addressRegistry;
    }

    /**
     * Lets the user choose a file to write to
     * Checks and readies the file for the writeToFile method
     *
     * @param addresses List to write to file
     * @throws IOException
     */
    public static void exportToFile(List<Address> addresses) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get(".").toAbsolutePath().normalize().toFile());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt files (*.txt)", "*.txt"));
        fileChooser.setTitle("Export CSV");
        File file = fileChooser.showSaveDialog(new Stage());

        if (file == null) throw new CancellationException();

        writeToFile(file, addresses);
    }

    /**
     * Opens file and writes data to it
     * Writes data from address list
     *
     * @param file      The file to write to
     * @param addresses The list to write
     * @throws IOException
     */
    private static void writeToFile(File file, List<Address> addresses) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        fileWriter.write("POSTAL_CODE \t PLACE \t COMMUNE_CODE \t COMMUNE \t POSTAL_CATEGORY\n");

        for (Address address : addresses) {
            fileWriter.write(address.getPostalCode().getCodeAsString() + "\t" +
                    address.getPlace().getName() + "\t" +
                    address.getCommune().getCommunalCode() + "\t" +
                    address.getCommune().getName() + "\t" +
                    address.getPostalCode().getPostalCategory() + "\n"
            );
        }

        fileWriter.close();
    }
}
