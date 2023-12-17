package j2ee_project.service.user;

import j2ee_project.repository.user.ModeratorRepository;
import j2ee_project.model.user.Moderator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Moderator service.
 */
@Service
public class ModeratorService {
    /**
     * The Moderator repository.
     */
    ModeratorRepository moderatorRepository;

    /**
     * Instantiates a new Moderator service.
     *
     * @param moderatorRepository the moderator repository
     */
    public ModeratorService(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    /**
     * Get the list of all moderators
     *
     * @return the list of all moderators
     */
    @Transactional
    public List<Moderator> getModerators(){
        return moderatorRepository.findAll();
    }

    /**
     * Get moderator by id
     *
     * @param moderatorId the moderator's id
     * @return the moderator
     */
    @Transactional
    public Moderator getModerator(int moderatorId){
        return moderatorRepository.findById(moderatorId);
    }

    /**
     * Delete moderator by id
     *
     * @param moderatorId the moderator's id
     */
    @Transactional
    public void deleteModerator(int moderatorId){
        moderatorRepository.deleteById(moderatorId);
    }

    /**
     * Add moderator
     *
     * @param moderator the moderator to be added
     */
    @Transactional
    public void addModerator(Moderator moderator){
        moderatorRepository.save(moderator);
    }
}
