package HospitalSystem.Person;

import HospitalSystem.Hospital.Department;

/**
 * Employees at {@link Department}
 */
public class Employee extends Person {


    /**
     * Creates an instance of Employee
     *
     * @see Person#Person(String, String, String)
     */
    public Employee(String firstName, String lastName, String socialSecurityNumber) {
        super(firstName, lastName, socialSecurityNumber);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
