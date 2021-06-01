package HospitalSystem.Person;


/**
 * Abstract class that contains data for a person, contains first name last name and social security number.
 */
public abstract class Person {
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;

    /**
     * Creates an instance of person
     *
     * @param firstName            The first name
     * @param lastName             The last name
     * @param socialSecurityNumber The social security number
     */
    protected Person(String firstName, String lastName, String socialSecurityNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /**
     * Get persons first name
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set persons first name
     *
     * @param firstName The first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get persons last name
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get persons full name
     *
     * @return First name and last name
     */
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }


    /**
     * Get social security number
     *
     * @return Social security number
     */
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }


    /**
     * Set new social security number
     *
     * @param socialSecurityNumber The new social security number to set
     */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                '}';
    }
}
