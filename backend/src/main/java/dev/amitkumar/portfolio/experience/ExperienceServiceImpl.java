package dev.amitkumar.portfolio.experience;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExperienceResponse> getExperiences() {
        return experienceRepository.findAllByOrderBySortOrderAscIdAsc().stream()
                .map(ExperienceResponse::from)
                .toList();
    }
}
