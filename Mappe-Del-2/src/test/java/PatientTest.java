import idatt2001.baardshe.patientregistry.patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatientTest {

    Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("00123456789", "patient", "1");
    }

    @Test
    public void validSocialSecurityNumberTest() {
        assertDoesNotThrow(() -> patient.setSocialSecurityNumber("00123456789"));
        assertDoesNotThrow(() -> patient.setSocialSecurityNumber("000 000 000 00"));
    }

    @Test
    public void invalidSocialSecurityNumberTest() {
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber("0"));
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber("0          "));
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber("           ")); // 11 spaces
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber("012 345 678 910")); //12
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber("012 345 A67 89"));
    }
}
