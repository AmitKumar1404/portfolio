package dev.amitkumar.portfolio.skill;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/skills")
@Tag(name = "Skills", description = "Public skill taxonomy")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    @Operation(summary = "List skills grouped by category")
    @ApiResponse(responseCode = "200", description = "Categories and skills ordered for display")
    public List<SkillResponse> getSkills() {
        return skillService.getSkills();
    }
}
