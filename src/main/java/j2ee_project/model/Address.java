package j2ee_project.model;

import j2ee_project.dto.AddressDTO;
import jakarta.persistence.*;

/**
 * Address
 */
@Entity
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "streetAddress", nullable = false, length = 60)
    private String streetAddress;
    @Basic
    @Column(name = "postalCode", nullable = false, length = 30)
    private String postalCode;
    @Basic
    @Column(name = "city", nullable = false, length = 60)
    private String city;
    @Basic
    @Column(name = "country", nullable = false, length = 60)
    private String country;

    /**
     * Instantiates a new Address.
     */
    public Address() {}

    /**
     * Instantiates a new Address.
     *
     * @param streetAddress the street address
     * @param postalCode    the postal code
     * @param city          the city
     * @param country       the country
     */
    public Address(String streetAddress, String postalCode, String city, String country) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    /**
     * Instantiates a new Address.
     *
     * @param addressDTO the address dto
     */
    public Address(AddressDTO addressDTO){
        this.streetAddress = addressDTO.getStreetAddress();
        this.postalCode = addressDTO.getPostalCode();
        this.city = addressDTO.getCity();
        this.country = addressDTO.getCountry();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets street address.
     *
     * @return the street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets street address.
     *
     * @param streetAddress the street address
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (streetAddress != null ? !streetAddress.equals(address.streetAddress) : address.streetAddress != null)
            return false;
        if (postalCode != null ? !postalCode.equals(address.postalCode) : address.postalCode != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (streetAddress != null ? streetAddress.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
