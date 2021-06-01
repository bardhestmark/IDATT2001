package HospitalSystem.Person;


/**
 * Variant of {@link Doctor}
 */
public class Surgeon extends Doctor {

    /**
     * Creates an instance of Surgeon
     *
     * @see Person#Person(String, String, String)
     */
    public Surgeon(String firstName, String lastName, String socialSecurityNumber) {
        super(firstName, lastName, socialSecurityNumber);
    }

    @Override
    void setDiagnosis(Patient patient, String diagnosis) {
        patient.setDiagnosis(diagnosis);
    }
}
