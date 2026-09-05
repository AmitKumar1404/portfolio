package dev.amitkumar.portfolio.project;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Projects", description = "Public featured and catalog projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @Operation(summary = "List published projects")
    @ApiResponse(responseCode = "200", description = "Published projects ordered by display order")
    public List<ProjectResponse> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get a published project by slug")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No published project with that slug",
                    content = @Content(schema = @Schema(implementation = dev.amitkumar.portfolio.common.api.ApiError.class)))
    })
    public ProjectResponse getProjectBySlug(@PathVariable String slug) {
        return projectService.getProjectBySlug(slug);
    }
}
