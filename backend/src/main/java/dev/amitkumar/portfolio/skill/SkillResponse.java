package dev.amitkumar.portfolio.skill;

import java.util.List;

public record SkillResponse(
        String category,
        String slug,
        int displayOrder,
        List<String> skills) {

    static SkillResponse from(SkillCategory category, List<Skill> skills) {
        return new SkillResponse(
                category.getName(),
                category.getSlug(),
                category.getSortOrder(),
                skills.stream().map(Skill::getName).toList());
    }
}
