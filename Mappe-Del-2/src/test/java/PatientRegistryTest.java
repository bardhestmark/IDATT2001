import idatt2001.baardshe.patientregistry.patient.Patient;
import idatt2001.baardshe.patientregistry.patient.PatientRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientRegistryTest {

    PatientRegistry patientRegistry;
    Patient patient1;
    Patient patient2;
    Patient patient3;

    @BeforeEach
    void setUp() {
        patientRegistry = new PatientRegistry();
        patient1 = new Patient("00123456789", "patient", "1");
        patient2 = new Patient("10123456789", "patient", "2");
        patient3 = new Patient(patient1.getSocialSecurityNumber(), "patient", "3");
    }

    @Test
    public void addPatient() {
        assertTrue(patientRegistry.addPatient(patient1));
        assertTrue(patientRegistry.addPatient(patient2));
        assertEquals(patientRegistry.getPatients().size(), 2);
        assertTrue(patientRegistry.getPatients().contains(patient1) && patientRegistry.getPatients().contains(patient2));
    }

    @Test
    public void addExistingPatient() {
        patientRegistry.addPatient(patient1);

        assertFalse(patientRegistry.addPatient(patient1));

        //patient3 has the same social security number as patient1
        assertFalse(patientRegistry.addPatient(patient3));

        assertEquals(patientRegistry.getPatients().size(), 1);
    }

    @Test
    public void removePatient() {
        patientRegistry.addPatient(patient1);
        patientRegistry.addPatient(patient2);

        assertTrue(patientRegistry.removePatient(patient2));
        assertEquals(patientRegistry.getPatients().size(), 1);
        assertFalse(patientRegistry.getPatients().contains(patient2));

        /*
        A bit counterintuitive, but trying to remove a patient with another patient
        object that only has matching social security should remove the patient.
        This is because social security should be unique, and is set as the only compared
        variable in the overridden equals method in the Patient class.
        */
        assertTrue(patientRegistry.removePatient(patient3));
        assertEquals(patientRegistry.getPatients().size(), 0);
    }

    @Test
    public void removeNonExistentPatient() {
        assertFalse(patientRegistry.removePatient(patient1));
        assertEquals(patientRegistry.getPatients().size(), 0);
    }
}
