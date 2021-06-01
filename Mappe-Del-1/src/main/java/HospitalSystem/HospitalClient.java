package HospitalSystem;


import HospitalSystem.Hospital.Department;
import HospitalSystem.Hospital.Hospital;
import HospitalSystem.Person.Employee;
import HospitalSystem.Person.Patient;

/**
 * Client class that tests some functionality of the hospital system
 */
public class HospitalClient {

    public static void main(String[] args) {
        Hospital hospital = new Hospital("TestHospital");
        HospitalTestData.fillRegisterWithTestData(hospital);
        Department department = hospital.getDepartments().get(0);
        Employee employeeThatExists = department.getEmployees().get(0);

        try {
            //prøver å fjerne en ansatt vi vet finnes
            department.remove(employeeThatExists);
            System.out.println("Employee removed");
        } catch (RemoveException e) {
            System.out.println(e);
        }

        Patient patientThatDoesNotExist = new Patient("Kari", "Erikkeher", "123");

        try {
            //prøver å fjerne en pasient som ikke finnes, denne skal feile
            department.remove(patientThatDoesNotExist);
            System.out.println("Patient removed");
        } catch (RemoveException e) {
            System.out.println(e);
        }

    }
}
