package dev.amitkumar.portfolio.profile;

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
@RequestMapping("/api/v1/profile")
@Tag(name = "Profile", description = "Public portfolio identity")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    @Operation(summary = "Get the public portfolio profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "No profile has been published",
                    content = @Content(schema = @Schema(implementation = dev.amitkumar.portfolio.common.api.ApiError.class)))
    })
    public ProfileResponse getProfile() {
        return profileService.getProfile();
    }
}
