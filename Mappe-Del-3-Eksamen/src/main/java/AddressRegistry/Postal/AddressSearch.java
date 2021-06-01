package AddressRegistry.Postal;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * Class for static {@link Address} search methods
 */
public class AddressSearch {

    /**
     * Searches a list of {@link Address} by {@link PostalCode}
     *
     * @param addresses The list to search on
     * @param search The string to search
     * @return A list of addresses that match the search
     */
    public static List<Address> searchByCode(List<Address> addresses, String search) {
        return addresses.stream().filter(address ->
                address.getPostalCode().getCodeAsString().contains(search)).collect(Collectors.toList());
    }

    /**
     * Searches a list of {@link Address} by {@link Place}
     *
     * @param addresses The list to search on
     * @param search The string to search
     * @return A list of addresses that match the search
     */
    public static List<Address> searchByPlace(List<Address> addresses, String search) {
        return addresses.stream().filter(address ->
                address.getPlace().getName().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
    }

    /**
     * Searches a list of {@link Address} by {@link Commune}
     *
     * @param addresses The list to search on
     * @param search The string to search
     * @return A list of addresses that match the search
     */
    public static List<Address> searchByCommune(List<Address> addresses, String search) {
        return addresses.stream().filter(address ->
                address.getCommune().getName().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
    }
}
