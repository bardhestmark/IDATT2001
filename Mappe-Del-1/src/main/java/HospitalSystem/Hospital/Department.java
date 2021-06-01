package HospitalSystem.Hospital;

import HospitalSystem.Person.Employee;
import HospitalSystem.Person.Patient;
import HospitalSystem.Person.Person;
import HospitalSystem.RemoveException;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Department of a {@link Hospital}
 */
public class Department {

    private String departmentName;
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();


    /**
     * Creates an instance of Department
     *
     * @param departmentName the name of the department
     */
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Adds a new employee to the list of employees
     * Checks for 'null' and if the employee is already in the list
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (!(employee == null || employees.contains(employee))) employees.add(employee);
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Adds a new patient to the list of patients
     * Checks for 'null' and if the patient is already in the list
     *
     * @param patient the patient to add
     */
    public void addPatient(Patient patient) {
        if (!(patient == null || patients.contains(patient))) patients.add(patient);
    }

    /**
     * Removes a patient or employee from their respective lists
     * Checks if the person given is an employee/patients and if they are
     * contained in the lists of the department
     *
     * @param person the person to find
     * @throws RemoveException if the Person/Object is not found
     */
    public void remove(Person person) throws RemoveException {
        if (person instanceof Patient && patients.contains(person)) patients.remove(person);
        else if (person instanceof Employee && employees.contains(person)) employees.remove(person);
        else throw new RemoveException("Person was not found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return departmentName.equals(that.departmentName) && employees.equals(that.employees) && patients.equals(that.patients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName, employees, patients);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentName='" + departmentName + '\'' +
                ", employees=" + employees +
                ", patients=" + patients +
                '}';
    }
}
