package j2ee_project.service.user;

import j2ee_project.repository.user.ForgottenPasswordRepository;
import j2ee_project.model.user.ForgottenPassword;
import j2ee_project.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class ForgottenPasswordService {
    private final ForgottenPasswordRepository forgottenPasswordRepository;

    public ForgottenPasswordService(ForgottenPasswordRepository forgottenPasswordRepository) {
        this.forgottenPasswordRepository = forgottenPasswordRepository;
    }

    /**
     * Add a forgotten password request
     *
     * @param forgottenPassword the forgotten password request
     */
    public void addForgottenPassword(ForgottenPassword forgottenPassword) {
        forgottenPasswordRepository.save(forgottenPassword);
    }

    /**
     * Remove a forgotten password request
     *
     * @param forgottenPassword the forgotten password request
     */
    public void removeForgottenPassword(ForgottenPassword forgottenPassword) {
        forgottenPasswordRepository.delete(forgottenPassword);
    }

    /**
     * Get forgotten password query from token
     *
     * @param token the token
     * @return the forgotten password
     */
    public ForgottenPassword getForgottenPasswordFromToken(String token){
        return forgottenPasswordRepository.getByToken(token);
    }

    /**
     * Get the user associated to the given forgotten password query
     *
     * @param forgottenPassword the forgotten password query
     * @return the user
     */
    public User getUser(ForgottenPassword forgottenPassword){
        ForgottenPassword forgottenPasswordDBObj = forgottenPasswordRepository.save(forgottenPassword);
        return forgottenPasswordDBObj.getUser();
    }
}
