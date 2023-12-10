package j2ee_project.service.address;

import j2ee_project.repository.address.AddressRepository;
import j2ee_project.model.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address newAddress) {
        return addressRepository.save(newAddress);
    }

    public Address addAddressIfNotExists(Address newAddress) {
        Address address = addressRepository.findAddressByStreetAddressAndPostalCodeAndCityAndCountry(newAddress.getStreetAddress(), newAddress.getPostalCode(), newAddress.getCity(), newAddress.getCountry());
        if(address == null) {
            address = addAddress(newAddress);
        }
        return address;
    }
}
