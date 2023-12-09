package j2ee_project.repository.user;

import j2ee_project.model.user.ForgottenPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ForgottenPasswordRepository extends JpaRepository<ForgottenPassword,Long> {
    ForgottenPassword getByToken(String token);
}
