package j2ee_project.repository.user;

import j2ee_project.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Customer repository to interacts with the Customer table in the DB
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(int id);
    void removeCustomerById(int id);

    int countCustomerByAddress_Id(int address_id);



}
