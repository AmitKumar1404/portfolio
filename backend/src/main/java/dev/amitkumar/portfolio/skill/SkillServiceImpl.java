package dev.amitkumar.portfolio.skill;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getSkills() {
        Map<Long, List<Skill>> grouped = new LinkedHashMap<>();
        Map<Long, SkillCategory> categories = new LinkedHashMap<>();

        for (Skill skill : skillRepository.findAllPublicOrdered()) {
            Long categoryId = skill.getCategory().getId();
            categories.putIfAbsent(categoryId, skill.getCategory());
            grouped.computeIfAbsent(categoryId, ignored -> new ArrayList<>()).add(skill);
        }

        return grouped.entrySet().stream()
                .map(entry -> SkillResponse.from(categories.get(entry.getKey()), entry.getValue()))
                .toList();
    }
}
