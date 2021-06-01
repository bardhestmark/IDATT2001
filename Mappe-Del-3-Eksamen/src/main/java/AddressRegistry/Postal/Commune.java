package AddressRegistry.Postal;

import java.util.Locale;
import java.util.Objects;

/**
 * Contains data for Commune
 */
public class Commune implements Comparable<Commune> {
    private String name;
    private final int communalCode; //this is not currently in use

    /**
     * @param name         The name of the commune
     * @param communalCode The communal code
     */
    public Commune(String name, int communalCode) {
        this.setName(name);
        if (communalCode < 1 || communalCode > 9999) throw new IllegalArgumentException("Invalid communal code");
        this.communalCode = communalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.equals("")) throw new IllegalArgumentException("Invalid commune name");
        this.name = name.toUpperCase(Locale.ROOT);
    }

    public int getCommunalCode() {
        return communalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commune commune = (Commune) o;
        return communalCode == commune.communalCode && Objects.equals(name, commune.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, communalCode);
    }

    @Override
    public int compareTo(Commune commune) {
        return Integer.compare(this.getName().compareTo(commune.getName()), 0);
    }
}
