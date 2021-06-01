package HospitalSystem.Person;


/**
 * Variant of {@link Employee}
 */
public class Nurse extends Employee {

    /**
     * Creates an instance of Nurse
     *
     * @see Person#Person(String, String, String)
     */
    public Nurse(String firstName, String lastName, String socialSecurityNumber) {
        super(firstName, lastName, socialSecurityNumber);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
