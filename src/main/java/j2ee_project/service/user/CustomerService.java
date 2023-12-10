package j2ee_project.service.user;

import j2ee_project.repository.address.AddressRepository;
import j2ee_project.repository.order.OrdersRepository;
import j2ee_project.repository.user.CustomerRepository;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CustomerRepository customerRepository;
    private final OrdersRepository ordersRepository;

    private final AddressRepository addressRepository;

    public CustomerService(CustomerRepository customerRepository, OrdersRepository ordersRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.ordersRepository = ordersRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public List<Customer> getCustomers(){
        return this.customerRepository.findAll();
    }

    /**
     * Get customer by ID
     *
     * @param customerId the customer's id
     * @return the customer
     */
    @Transactional
    public Customer getCustomer(int customerId){
        return this.customerRepository.findCustomerById(customerId);
    }

    /**
     * Delete customer by ID
     *
     * @param customerId the customer's id
     */
    @Transactional
    public void deleteCustomer(int customerId){
        this.customerRepository.removeCustomerById(customerId);
    }

    /**
     * Add a customer
     *
     * @param customer the customer
     */
    @Transactional
    public void addCustomer(Customer customer){
        this.customerRepository.save(customer);
    }

    /**
     * Edit a customer
     *
     * @param customer Customer's new data (but with the same id)
     */
    @Transactional
    public void modifyCustomer(Customer customer){

        Customer customerToBeEdited = null;
        try {
            customerToBeEdited = this.customerRepository.findCustomerById(customer.getId());

            if (!customer.getFirstName().isEmpty()){
                customerToBeEdited.setFirstName(customer.getFirstName());
            }
            if (!customer.getLastName().isEmpty()){
                customerToBeEdited.setLastName(customer.getLastName());
            }
            if (customer.getPassword()!=null){
                customerToBeEdited.setPassword(customer.getPassword());
            }
            if (!customer.getEmail().isEmpty()){
                customerToBeEdited.setEmail(customer.getEmail());
            }
            if (!customer.getPhoneNumber().isEmpty()){
                customerToBeEdited.setPhoneNumber(customer.getPhoneNumber());
            }

            Address oldAddress = customerToBeEdited.getAddress();
            Address newAddress;
            boolean isDetached = false;

            if (oldAddress!=null){
                newAddress = oldAddress;
                int nbCustomerWithSameAddress = this.customerRepository.countCustomerByAddress_Id(oldAddress.getId());

                if (nbCustomerWithSameAddress>1){
                    entityManager.detach(newAddress);
                    newAddress.setId(0); // Reset the ID to add a new Address with a new auto-generated ID
                    isDetached = true;
                }
            }else {
                newAddress = new Address();
            }


            if (!customer.getAddress().getStreetAddress().isEmpty()){
                newAddress.setStreetAddress(customer.getAddress().getStreetAddress());
            }
            if (!customer.getAddress().getPostalCode().isEmpty()){
                newAddress.setPostalCode(customer.getAddress().getPostalCode());
            }
            if (!customer.getAddress().getCity().isEmpty()){
                newAddress.setCity(customer.getAddress().getCity());
            }
            if (!customer.getAddress().getCountry().isEmpty()){
                newAddress.setCountry(customer.getAddress().getCountry());
            }

            if(isDetached) {
                addressRepository.save(newAddress);
            }

            this.customerRepository.save(customerToBeEdited);


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
