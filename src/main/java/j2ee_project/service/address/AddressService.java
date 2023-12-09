package j2ee_project.service.address;

import j2ee_project.dao.address.AddressRepository;
import j2ee_project.model.Address;

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
        if(address != null) {
            address = addAddress(newAddress);
        }
        return address;
    }
}
