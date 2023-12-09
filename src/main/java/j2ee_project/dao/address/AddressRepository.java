package j2ee_project.dao.address;

import j2ee_project.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the Address table in the DB
 *
 * @author Robin Meneust
 */
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findAddressByStreetAddressAndPostalCodeAndCityAndCountry(String streetAddress, String postalCode, String city, String country);
}
