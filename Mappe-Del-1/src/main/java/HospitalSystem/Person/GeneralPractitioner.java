package HospitalSystem.Person;

public class GeneralPractitioner extends Doctor {

    /**
     * Creates an instance of GeneralPractitioner
     *
     * @see Person#Person(String, String, String)
     */
    public GeneralPractitioner(String firstName, String lastName, String socialSecurityNumber) {
        super(firstName, lastName, socialSecurityNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void setDiagnosis(Patient patient, String diagnosis) {
        patient.setDiagnosis(diagnosis);
    }
}
