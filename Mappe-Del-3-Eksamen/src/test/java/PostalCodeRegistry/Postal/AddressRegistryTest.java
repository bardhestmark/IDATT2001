package AddressRegistry.Postal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the address registry
 */
class AddressRegistryTest {

    AddressRegistry addressRegistry;
    Address address1;
    Address address2;
    Address address3;

    @BeforeEach
    void setUp() {
        addressRegistry = new AddressRegistry();
        address1 = new Address(new Commune("Trondheim", 1), new Place("Gløs"), new PostalCode(1111, 'P'));
        address2 = new Address(new Commune("Oslo", 2), new Place("Oslo"), new PostalCode(2222, 'G'));
        address3 = new Address(address1.getCommune(), address2.getPlace(), new PostalCode(3333, 'B'));
    }

    @Test
    @DisplayName("Testing add address to registry")
    void addAddress() {
        assertTrue(addressRegistry.addAddress(address1));
        assertTrue(addressRegistry.addAddress(address2));
        assertEquals(addressRegistry.getAddressList().size(), 2);
        assertTrue(addressRegistry.getAddressList().contains(address1) && addressRegistry.getAddressList().contains(address2));
    }

    @Test
    @DisplayName("Testing adding of existing address to registry")
    void addExistingAddress() {
        addressRegistry.addAddress(address1);
        assertFalse(addressRegistry.addAddress(address1));
    }

    @Test
    @DisplayName("Testing removal of address from registry")
    void removeAddress() {
        addressRegistry.addAddress(address1);
        addressRegistry.addAddress(address2);

        assertTrue(addressRegistry.removeAddress(address2));
        assertEquals(addressRegistry.getAddressList().size(), 1);
        assertFalse(addressRegistry.getAddressList().contains(address2));
    }

    @Test
    @DisplayName("Testing removal of non existent address from registry")
    void removeNonExistentAddress() {
        assertFalse(addressRegistry.removeAddress(address1));
        assertEquals(addressRegistry.getAddressList().size(), 0);
    }

    @Test
    @DisplayName("Testing removal of address from registry given a post code")
    void removePostalCode() {
        addressRegistry.addAddress(address1);
        addressRegistry.addAddress(address2);

        assertTrue(addressRegistry.removePostalCode(address1.getPostalCode()));
        assertEquals(addressRegistry.getAddressList().size(), 1);
    }

    @Test
    @DisplayName("Testing removal of place from registry")
    void removePlace() {
        addressRegistry.addAddress(address1);
        addressRegistry.addAddress(address2);
        addressRegistry.addAddress(address3);

        assertTrue(addressRegistry.removePlace(address3.getPlace()));
        assertEquals(addressRegistry.getAddressList().size(), 1);
    }

    @Test
    @DisplayName("Testing removal of commune from registry")
    void removeCommune() {
        addressRegistry.addAddress(address1);
        addressRegistry.addAddress(address2);
        addressRegistry.addAddress(address3);

        assertTrue(addressRegistry.removeCommune(address3.getCommune()));
        assertEquals(addressRegistry.getAddressList().size(), 1);
    }
}