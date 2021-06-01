package AddressRegistry.Postal;

import java.util.Comparator;

/**
 * Class for static {@link Address} comparators
 */
public class AddressComparators {

    /**
     * Sorts addresses by their {@link PostalCode}
     */
    public static Comparator<Address> sortByPostalCode = Comparator.comparingInt(o -> o.getPostalCode().getCode());

    /**
     * Sorts addresses by their {@link Place}
     */
    public static Comparator<Address> sortByPlace = (o1, o2) -> {
        if (o1.getPlace() == null && o2.getPlace() != null) {
            return -1;
        }
        if (o1.getPlace() != null && o2.getPlace() == null) {
            return 1;
        }
        if (o1.getPlace() != null && o2.getPlace() != null) {
            if (o1.getPlace().compareTo(o2.getPlace()) < 0) {
                return -1;
            }
            if (o1.getPlace().compareTo(o2.getPlace()) > 0) {
                return 1;
            }
        }
        return 0;
    };

    /**
     * Sorts addresses by their {@link Commune}
     */
    public static Comparator<Address> sortByCommune = (o1, o2) -> {
        if (o1.getCommune() == null && o2.getCommune() != null) {
            return -1;
        }
        if (o1.getCommune() != null && o2.getCommune() == null) {
            return 1;
        }
        if (o1.getCommune() != null && o2.getCommune() != null) {
            if (o1.getCommune().compareTo(o2.getCommune()) < 0) {
                return -1;
            }
            if (o1.getCommune().compareTo(o2.getCommune()) > 0) {
                return 1;
            }
        }
        return 0;
    };
}
