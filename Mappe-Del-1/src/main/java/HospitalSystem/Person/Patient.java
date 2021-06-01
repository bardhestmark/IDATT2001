package HospitalSystem.Person;


import HospitalSystem.Hospital.Hospital;

/**
 * Patient which is admitted to {@link Hospital}
 */
public class Patient extends Person implements Diagnosable {

    private String diagnosis = ""; //init to empty string

    /**
     * Creates an instance of Patient
     *
     * @see Person#Person(String, String, String)
     */
    public Patient(String firstName, String lastName, String socialSecurityNumber) {
        super(firstName, lastName, socialSecurityNumber);
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
