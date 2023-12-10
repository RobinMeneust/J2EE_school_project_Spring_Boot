package j2ee_project.repository.user;

import j2ee_project.model.user.ForgottenPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Forgotten password repository.
 */
@Repository
public interface ForgottenPasswordRepository extends JpaRepository<ForgottenPassword,Long> {
    /**
     * Search a forgotten password in the database according to its token
     *
     * @param token the token
     * @return the found forgotten password
     */
    ForgottenPassword getByToken(String token);
}
