package j2ee_project.dao.user;

import j2ee_project.model.user.ForgottenPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgottenPasswordRepository extends JpaRepository<ForgottenPassword,Long> {
    ForgottenPassword getByToken(String token);
}
