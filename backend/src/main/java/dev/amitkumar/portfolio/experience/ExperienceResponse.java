package dev.amitkumar.portfolio.experience;

import java.time.LocalDate;
import java.util.List;

public record ExperienceResponse(
        String company,
        String role,
        EmploymentType employmentType,
        String location,
        String companyUrl,
        LocalDate startDate,
        LocalDate endDate,
        boolean current,
        String description,
        List<String> highlights,
        int displayOrder) {

    static ExperienceResponse from(Experience experience) {
        List<String> highlights = experience.getHighlights().stream()
                .map(ExperienceHighlight::getBody)
                .toList();
        return new ExperienceResponse(
                experience.getCompany(),
                experience.getRole(),
                experience.getEmploymentType(),
                experience.getLocation(),
                experience.getCompanyUrl(),
                experience.getStartDate(),
                experience.getEndDate(),
                experience.isCurrent(),
                experience.getDescription(),
                highlights,
                experience.getSortOrder());
    }
}
