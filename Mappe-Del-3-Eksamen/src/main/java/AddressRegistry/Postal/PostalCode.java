package AddressRegistry.Postal;

import java.util.Objects;

/**
 * Contains data for PostalCode
 */
public class PostalCode implements Comparable<PostalCode> {
    private final int code;
    private char postalCategory;

    /**
     * @param code           The postal code as int
     * @param postalCategory The category of the postal code
     */
    public PostalCode(int code, char postalCategory) {
        if (code < 1 || code > 9999) throw new IllegalArgumentException("Invalid integer for postal code");
        this.code = code;
        this.postalCategory = postalCategory;
    }

    public int getCode() {
        return code;
    }

    public char getPostalCategory() {
        return postalCategory;
    }

    public void setPostalCategory(char postalCategory) {
        this.postalCategory = postalCategory;
    }

    public String getCodeAsString() {
        return String.format("%04d", code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalCode that = (PostalCode) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public int compareTo(PostalCode o) {
        return this.code - o.code;
    }
}
