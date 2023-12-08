package j2ee_project.dto;

import j2ee_project.model.Address;

/**
 * This class is a Data Transfer Object which contain and check Customer data
 *
 * @author Lucas VELAY
 */
public class CustomerDTO extends UserDTO{

    private Address address;

    /**
     * Instantiates a new Customer dto.
     *
     * @param firstName       the first name
     * @param lastName        the last name
     * @param email           the email
     * @param password        the password
     * @param confirmPassword the confirm password (string that must match with the password)
     * @param phoneNumber     the phone number
     */
    public CustomerDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        super(firstName, lastName, email, password, confirmPassword, phoneNumber);
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", email=" + this.getEmail() +
                ", password=" + this.getPassword() +
                ", confirmPassword=" + this.getConfirmPassword() +
                ", phoneNumber=" + this.getPhoneNumber() +
                "address=" + this.getAddress() +
                '}';
    }
}