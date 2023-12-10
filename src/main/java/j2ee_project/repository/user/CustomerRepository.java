package j2ee_project.repository.user;

import j2ee_project.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Customer repository to interacts with the Customer table in the DB
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Search customer in the database according to its id
     *
     * @param id the id
     * @return the found customer
     */
    Customer findCustomerById(int id);

    /**
     * Remove customer in the database according to its id
     *
     * @param id the id
     */
    void removeCustomerById(int id);

    /**
     * Count customer in the database according to its address
     *
     * @param address_id the address id
     * @return the number of found customers
     */
    int countCustomerByAddress_Id(int address_id);

}
