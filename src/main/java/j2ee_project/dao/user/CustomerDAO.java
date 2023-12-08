package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Class that interact with the database to edit the Customer table in the database
 *
 * @author Robin Meneust
 */
public class CustomerDAO {
    /**
     * Get the list of all customers
     *
     * @return the list of customers
     */
    public static List<Customer> getCustomers(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Customer> customers = entityManager.createQuery("FROM Customer",Customer.class).getResultList();

        transaction.commit();
        entityManager.close();
        return customers;
    }

    /**
     * Get customer by ID
     *
     * @param customerId the customer's id
     * @return the customer
     */
    public static Customer getCustomer(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = null;
        try {
            customer = entityManager.createQuery("FROM Customer WHERE id=:customerId",Customer.class).setParameter("customerId",customerId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return customer;
    }

    /**
     * Delete customer by ID
     *
     * @param customerId the customer's id
     */
    public static void deleteCustomer(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = entityManager.find(Customer.class,customerId);
        if(customer == null) {
            System.err.println("The customer to be deleted does not exist");
            entityManager.close();
            return;
        }
        entityManager.remove(customer);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Add a customer
     *
     * @param customer the customer
     */
    public static void addCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Edit a customer
     *
     * @param customer Customer's new data (but with the same id)
     */
    public static void modifyCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customerToBeEdited = null;
        try {
            customerToBeEdited = entityManager.createQuery("FROM Customer WHERE id = :customerId", Customer.class).setParameter("customerId", customer.getId()).getSingleResult();

            if (!customer.getFirstName().isEmpty()){
                customerToBeEdited.setFirstName(customer.getFirstName());
            }
            if (!customer.getLastName().isEmpty()){
                customerToBeEdited.setLastName(customer.getLastName());
            }
            if (!customer.getPassword().isEmpty()){
                customerToBeEdited.setPassword(customer.getPassword());
            }
            if (!customer.getEmail().isEmpty()){
                customerToBeEdited.setEmail(customer.getEmail());
            }
            if (!customer.getPhoneNumber().isEmpty()){
                customerToBeEdited.setPhoneNumber(customer.getPhoneNumber());
            }

            Address oldAddress = customerToBeEdited.getAddress();
            Address newAddress = oldAddress;
            boolean isDetached = false;

            long nbAddressRefsInCustomer = entityManager.createQuery("SELECT COUNT(*) FROM Customer WHERE address.id = :addressId", Long.class)
                    .setParameter("addressId", oldAddress.getId())
                    .getSingleResult();

            long nbAddressRefsInOrders = entityManager.createQuery("SELECT COUNT(*) FROM Orders WHERE address.id = :addressId", Long.class)
                    .setParameter("addressId", oldAddress.getId())
                    .getSingleResult();

            // Check if any referencing entities exist
            if (nbAddressRefsInCustomer+nbAddressRefsInOrders > 1) {
                // Refs exist so we create a new Address entry
                newAddress = oldAddress;
                entityManager.detach(newAddress);
                newAddress.setId(0); // Reset the ID to add a new Address with a new auto-generated ID
                isDetached = true;
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
                entityManager.persist(newAddress);
            }

            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        entityManager.close();
    }
}
