package j2ee_project.service.address;

import j2ee_project.repository.address.AddressRepository;
import j2ee_project.model.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class of service to manage Address table
 *
 * @author Robin Meneust
 */
@Service
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

    /**
     * Instantiates a new Address service.
     *
     * @param addressRepository the address repository
     */
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Add address
     *
     * @param newAddress the new address
     * @return the address
     */
    public Address addAddress(Address newAddress) {
        return addressRepository.save(newAddress);
    }

    /**
     * Add and return the given address if it does not exist or return the old one if it does
     *
     * @param newAddress the new address
     * @return the address added if it didn't exist or the old one if it did exist
     */
    public Address addAddressIfNotExists(Address newAddress) {
        Address address = addressRepository.findAddressByStreetAddressAndPostalCodeAndCityAndCountry(newAddress.getStreetAddress(), newAddress.getPostalCode(), newAddress.getCity(), newAddress.getCountry());
        if(address == null) {
            address = addAddress(newAddress);
        }
        return address;
    }
}
