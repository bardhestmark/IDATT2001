package HospitalSystem.Hospital;

import java.util.ArrayList;


/**
 * Class that contains a hospital, with a name and a list of departments
 */
public class Hospital {

    private String hospitalName;
    private ArrayList<Department> departments = new ArrayList<>();


    /**
     * Creates an instance of a Hospital
     *
     * @param hospitalName the name of the hospital
     */
    public Hospital(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    /**
     * Adds a new department to the list of departments
     * Checks for 'null' and if the department is already in the list
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (!(department == null || departments.contains(department))) departments.add(department);
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalName='" + hospitalName + '\'' +
                '}';
    }
}
