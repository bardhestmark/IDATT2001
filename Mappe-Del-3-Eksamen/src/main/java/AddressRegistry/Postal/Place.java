package AddressRegistry.Postal;

import java.util.Locale;
import java.util.Objects;

/**
 * Contains data for Place
 */
public class Place implements Comparable<Place> {

    private String name;

    /**
     * @param name The name of the place
     */
    public Place(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.equals("")) throw new IllegalArgumentException("Invalid place name");
        this.name = name.toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place that = (Place) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Place place) {
        return Integer.compare(this.getName().compareTo(place.getName()), 0);
    }
}
