import HospitalSystem.*;
import HospitalSystem.Hospital.Department;
import HospitalSystem.Hospital.Hospital;
import HospitalSystem.Person.Employee;
import HospitalSystem.Person.Patient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepartmentTest {

    @Test
    void removePatientTest() throws RemoveException {
        Hospital hospital = new Hospital("TestHospital");
        HospitalTestData.fillRegisterWithTestData(hospital);
        Department department = hospital.getDepartments().get(0);
        Patient patient = department.getPatients().get(0);

        assertEquals(2, department.getPatients().size());
        department.remove(patient);
        assertEquals(1, department.getPatients().size());
    }

    @Test
    void removeEmployeeTest() throws RemoveException {
        Hospital hospital = new Hospital("TestHospital");
        HospitalTestData.fillRegisterWithTestData(hospital);
        Department department = hospital.getDepartments().get(0);
        Employee employee = department.getEmployees().get(0);

        assertEquals(7, department.getEmployees().size());
        department.remove(employee);
        assertEquals(6, department.getEmployees().size());
    }

    @Test
    void removeNonExistentEmployeeTest() {
        Hospital hospital = new Hospital("TestHospital");
        HospitalTestData.fillRegisterWithTestData(hospital);
        Department department = hospital.getDepartments().get(0);
        Employee employee = new Employee("Test", "Test", "123");

        assertThrows(RemoveException.class, () -> department.remove(employee));

        assertThrows(RemoveException.class, () -> department.remove(null));
    }
}
