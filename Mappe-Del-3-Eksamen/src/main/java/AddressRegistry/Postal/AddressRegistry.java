package AddressRegistry.Postal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for handling a list of Addresses
 */
public class AddressRegistry {

    private final HashMap<Integer, Address> addressMap;
    private final List<Place> placeList;
    private final List<Commune> communeList;

    public AddressRegistry() {
        this.addressMap = new HashMap<>();
        this.placeList = new ArrayList<>();
        this.communeList = new ArrayList<>();
    }

    /**
     * Adds an {@link Address} if it is not already in the list
     * Connects objects of {@link Place} and {@link Commune} so that not
     * more than one of each equal object is saved, adding them to lists
     *
     * @param address The {@link Address} to add
     * @return if successfully added
     */
    public boolean addAddress(Address address) {
        if(address == null) return false;
        if (!addressMap.containsKey(address.getPostalCode().getCode())) {

            //making sure all matching places and communes are the same objects
            if (placeList.contains(address.getPlace())) {
                address.setPlace(placeList.get(placeList.indexOf(address.getPlace())));
            } else placeList.add(address.getPlace());

            if (communeList.contains(address.getCommune())) {
                address.setCommune(communeList.get(communeList.indexOf(address.getCommune())));
            } else communeList.add(address.getCommune());

            addressMap.put(address.getPostalCode().getCode(), address);
            return true;
        }
        return false;
    }

    /**
     * Removes a {@link Address} if contained in the registry
     *
     * @param address The {@link Address} to remove
     * @return if successfully removed
     */
    public boolean removeAddress(Address address) {
        return addressMap.remove(address.getPostalCode().getCode()) != null;
    }

    /**
     * Removes and address given postal code as argument
     *
     * @param postalCode The {@link PostalCode} of the {@link Address} to remove
     * @return if successfully removed
     */
    public boolean removePostalCode(PostalCode postalCode) {
        return addressMap.remove(postalCode.getCode()) != null;
    }

    /**
     * Gets an {@link Address} given a {@link PostalCode} code
     * As a unique key, searches the hashlist with O(1)
     *
     * @param postalCode The code to search for
     * @return The {@link Address} if found
     */
    public Address getAddressGivenPostalCode(int postalCode) {
        return addressMap.get(postalCode);
    }


    /**
     * Removes a {@link Place} and all instances of {@link Address} connected to it
     *
     * @param place The place to remove
     * @return if successfully removed
     */
    public boolean removePlace(Place place) {
        return addressMap.values().removeIf(p -> p.getPlace().equals(place));
    }

    /**
     * Removes a {@link Commune} and all instances of {@link Address} connected to it
     *
     * @param commune The commune to remove
     * @return if successfully removed
     */
    public boolean removeCommune(Commune commune) {
        return addressMap.values().removeIf(p -> p.getCommune().equals(commune));
    }

    /**
     * @return a list of all addresses in the registry
     */
    public List<Address> getAddressList() {
        return new ArrayList<>(addressMap.values());
    }
}
