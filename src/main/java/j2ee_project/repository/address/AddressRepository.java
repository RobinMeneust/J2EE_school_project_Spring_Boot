package j2ee_project.repository.address;

import j2ee_project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the Address table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    /**
     * Search an address in the database with according to characteristic
     *
     * @param streetAddress the street address
     * @param postalCode    the postal code
     * @param city          the city
     * @param country       the country
     * @return the found address
     */
    Address findAddressByStreetAddressAndPostalCodeAndCityAndCountry(String streetAddress, String postalCode, String city, String country);
}
