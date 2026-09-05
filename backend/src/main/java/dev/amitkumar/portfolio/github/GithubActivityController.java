package dev.amitkumar.portfolio.github;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/github/activity")
@Tag(name = "GitHub", description = "Public GitHub activity")
public class GithubActivityController {

    private final GithubActivityService githubActivityService;

    public GithubActivityController(GithubActivityService githubActivityService) {
        this.githubActivityService = githubActivityService;
    }

    @GetMapping
    @Operation(
            summary = "Get recent public GitHub activity",
            description = "Returns a normalized, bounded summary of public GitHub events. "
                    + "Fresh results are cached; stale cache is returned if GitHub is unavailable.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Normalized GitHub activity"),
            @ApiResponse(
                    responseCode = "503",
                    description = "GitHub is unavailable and no cached activity exists",
                    content = @Content(schema = @Schema(implementation = dev.amitkumar.portfolio.common.api.ApiError.class)))
    })
    public GithubActivityResponse getActivity() {
        return githubActivityService.getActivity();
    }
}
