package idatt2001.baardshe.patientregistry.patient;

import java.util.ArrayList;
import java.util.List;


/**
 * A registry containing {@link Patient}
 */
public class PatientRegistry {
    private ArrayList<Patient> patients;


    /**
     * Creates an instance of PatientRegistry
     */
    public PatientRegistry() {
        this.patients = new ArrayList<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    /**
     * Adds a patient if not already in list
     *
     * @param patient Patient to add
     * @return if the patient was successfully added
     */
    public boolean addPatient(Patient patient) {
        if (patient != null && !patients.contains(patient)) {
            return patients.add(patient);
        } else return false;
    }

    /**
     * Removes patient from list if it exists
     *
     * @param patient The patient to remove
     * @return if the patient was successfully removed
     */
    public boolean removePatient(Patient patient) {
        return patients.remove(patient);
    }
}
