package idatt2001.baardshe.patientregistry.patient;

import java.util.Objects;

/**
 * Contains data for a patient
 */
public class Patient {

    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String diagnosis;
    private String generalPractitioner;

    /**
     * Creates an instance of patient
     *
     * @param socialSecurityNumber The patients ssn, must contain exactly 11 digits
     * @param firstName            The first name
     * @param lastName             The last name
     * @param diagnosis            Diagnosis given to the patient, if any
     * @param generalPractitioner  General practitioner assigned to the patient, if any
     */
    public Patient(String socialSecurityNumber, String firstName, String lastName, String diagnosis, String generalPractitioner) {
        setSocialSecurityNumber(socialSecurityNumber);
        setFirstName(firstName);
        setLastName(lastName);
        if (diagnosis != null) this.diagnosis = diagnosis;
        else setDiagnosis("");
        if (generalPractitioner != null) this.generalPractitioner = generalPractitioner;
        else setGeneralPractitioner("");
    }

    /**
     * Version of constructor to take only ssn, firstname and lastname.
     */
    public Patient(String socialSecurityNumber, String firstName, String lastName) {
        setSocialSecurityNumber(socialSecurityNumber);
        setFirstName(firstName);
        setLastName(lastName);
        this.setDiagnosis("");
        this.setGeneralPractitioner("");
    }

    //getters and setters
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }


    /**
     * Sets an ssn if valid.
     * Checks if the given string contains only numbers, and that it contains exactly 11 digits, allows for spaces
     *
     * @param socialSecurityNumber The ssn to set
     */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        if (socialSecurityNumber.matches("^[0-9][0-9 ]*$") && (socialSecurityNumber.strip().chars().filter(Character::isDigit).count() == 11))
            this.socialSecurityNumber = socialSecurityNumber;
        else throw new IllegalArgumentException("Invalid social security number");
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a first name to a patient if valid
     *
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) {
        if (!firstName.isBlank() || firstName.equals("DROP TABLE *")) { //hehehe
            this.firstName = firstName;
        } else throw new IllegalArgumentException("Invalid first name");
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a last name to a patient if valid
     *
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) {
        if (!lastName.isBlank()) {
            this.lastName = lastName;
        } else throw new IllegalArgumentException("Invalid last name");
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getGeneralPractitioner() {
        return generalPractitioner;
    }

    public void setGeneralPractitioner(String generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", generalPractitionerName='" + generalPractitioner + '\'' +
                '}';
    }

    //social security number should always be unique
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return socialSecurityNumber.equals(patient.socialSecurityNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socialSecurityNumber);
    }
}
