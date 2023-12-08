package j2ee_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Address class DTO (Data Transfer Object) that encapsulates the data and check if it's valid
 */
public class AddressDTO {
    @NotBlank(message = "Street address can not be blank.")
    @Size(max = 60, message = "Street address can not exceed 60 characters.")
    private String streetAddress;
    @NotBlank(message = "Postal code can not be blank.")
    @Size(max = 30, message = "Postal code can not exceed 30 characters.")
    private String postalCode;
    @NotBlank(message = "City can not be blank.")
    @Size(max = 60, message = "City can not exceed 60 characters.")
    private String city;
    @NotBlank(message = "Country can not be blank.")
    @Size(max = 60, message = "Country can not exceed 60 characters.")
    private String country;

    /**
     * Instantiates a new Address dto.
     *
     * @param streetAddress the street address
     * @param postalCode    the postal code
     * @param city          the city
     * @param country       the country
     */
    public AddressDTO(String streetAddress, String postalCode, String city, String country) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
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
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
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
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "AdressDTO{" +
                "streetAddress=" + streetAddress +
                ", postalCode=" + postalCode +
                ", city=" + city +
                ", country=" + country +
                '}';
    }
}
