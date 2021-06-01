package HospitalSystem.Person;


/**
 * Variant of {@link} Employee
 */
public abstract class Doctor extends Employee {

    /**
     * Creates an instance of Doctor
     *
     * @see Person#Person(String, String, String)
     */
    public Doctor(String firstName, String lastName, String socialSecurityNumber) {
        super(firstName, lastName, socialSecurityNumber);
    }


    /**
     * Set a diagnosis on a given {@link Patient}
     *
     * @param patient   The patient to diagnose
     * @param diagnosis The diagnosis to assign to the patient
     */
    abstract void setDiagnosis(Patient patient, String diagnosis);
}
