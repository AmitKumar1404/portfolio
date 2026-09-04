package dev.amitkumar.portfolio.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.amitkumar.portfolio.common.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    private ProfileServiceImpl profileService;

    @BeforeEach
    void setUp() {
        profileService = new ProfileServiceImpl(profileRepository);
    }

    @Test
    void getProfileReturnsMappedDto() {
        when(profileRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(ProfileFixtures.amitKumar()));

        ProfileResponse response = profileService.getProfile();

        assertThat(response.name()).isEqualTo("Amit Kumar");
        assertThat(response.headline()).isEqualTo("Software Engineer");
        assertThat(response.availability()).isEqualTo(Availability.SELECTIVE);
        assertThat(response.githubUrl()).isEqualTo("https://github.com/AmitKumar1404");
    }

    @Test
    void getProfileThrowsWhenMissing() {
        when(profileRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> profileService.getProfile())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Profile not found")
                .extracting(ex -> ((ResourceNotFoundException) ex).getCode())
                .isEqualTo("PROFILE_NOT_FOUND");
    }
}
