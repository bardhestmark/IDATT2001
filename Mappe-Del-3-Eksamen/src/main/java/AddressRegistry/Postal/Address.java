package AddressRegistry.Postal;

/**
 * Contains data for Address
 */
public class Address {

    private Commune commune;
    private Place place;
    private PostalCode postalCode;

    /**
     * Creates an instance of Address
     *
     * @param commune    The {@link Commune}
     * @param place      The {@link Place}
     * @param postalCode The {@link PostalCode}
     */
    public Address(Commune commune, Place place, PostalCode postalCode) {
        this.commune = commune;
        this.place = place;
        this.postalCode = postalCode;
    }

    //getters and setters
    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
}
