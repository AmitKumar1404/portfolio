package dev.amitkumar.portfolio.experience;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/experiences")
@Tag(name = "Experience", description = "Public work experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    @Operation(summary = "List work experiences")
    @ApiResponse(responseCode = "200", description = "Experiences ordered by display order")
    public List<ExperienceResponse> getExperiences() {
        return experienceService.getExperiences();
    }
}
