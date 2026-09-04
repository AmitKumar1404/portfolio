package dev.amitkumar.portfolio.profile;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileRepository profileRepository;

    @Test
    void getProfileWhenPresentReturns200() throws Exception {
        when(profileRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(ProfileFixtures.amitKumar()));

        mockMvc.perform(get("/api/v1/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Amit Kumar"))
                .andExpect(jsonPath("$.headline").value("Software Engineer"))
                .andExpect(jsonPath("$.shortBio").value("Short bio"))
                .andExpect(jsonPath("$.longBio").value("Long bio"))
                .andExpect(jsonPath("$.location").value("Bengaluru"))
                .andExpect(jsonPath("$.email").value("amit@example.com"))
                .andExpect(jsonPath("$.githubUrl").value("https://github.com/AmitKumar1404"))
                .andExpect(jsonPath("$.availability").value("SELECTIVE"))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    void getProfileWhenMissingReturns404() throws Exception {
        when(profileRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/profile"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value("PROFILE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Profile not found"))
                .andExpect(jsonPath("$.path").value("/api/v1/profile"));
    }
}
