package j2ee_project.service.user;

import j2ee_project.repository.user.ModeratorRepository;
import j2ee_project.model.user.Moderator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratorService {
    ModeratorRepository moderatorRepository;

    public ModeratorService(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    /**
     * Get the list of all moderators
     *
     * @return the list of all moderators
     */
    public List<Moderator> getModerators(){
        return moderatorRepository.findAll();
    }

    /**
     * Get moderator by id
     *
     * @param moderatorId the moderator's id
     * @return the moderator
     */
    public Moderator getModerator(int moderatorId){
        return moderatorRepository.findById(moderatorId);
    }

    /**
     * Delete moderator by id
     *
     * @param moderatorId the moderator's id
     */
    public void deleteModerator(int moderatorId){
        moderatorRepository.deleteById(moderatorId);
    }

    /**
     * Add moderator
     *
     * @param moderator the moderator to be added
     */
    public void addModerator(Moderator moderator){
        moderatorRepository.save(moderator);
    }
}
