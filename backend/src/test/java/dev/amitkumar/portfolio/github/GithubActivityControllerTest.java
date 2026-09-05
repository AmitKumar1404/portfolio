package dev.amitkumar.portfolio.github;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.amitkumar.portfolio.contact.ContactMessageRepository;
import dev.amitkumar.portfolio.experience.ExperienceRepository;
import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;
import dev.amitkumar.portfolio.skill.SkillRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GithubActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GithubCacheRepository githubCacheRepository;

    @MockitoBean
    private GithubClient githubClient;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private ProjectRepository projectRepository;

    @MockitoBean
    private ExperienceRepository experienceRepository;

    @MockitoBean
    private SkillRepository skillRepository;

    @MockitoBean
    private ContactMessageRepository contactMessageRepository;

    @Test
    void anonymousRequestReturns200WithPublicShape() throws Exception {
        when(githubCacheRepository.findByCacheKey(any())).thenReturn(Optional.empty());
        when(githubClient.fetchPublicEvents()).thenReturn(List.of(GithubFixtures.pushEvent()));
        when(githubCacheRepository.save(any(GithubCache.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(get("/api/v1/github/activity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("AmitKumar1404"))
                .andExpect(jsonPath("$.profileUrl").value("https://github.com/AmitKumar1404"))
                .andExpect(jsonPath("$.source").value("github"))
                .andExpect(jsonPath("$.cached").value(false))
                .andExpect(jsonPath("$.stale").value(false))
                .andExpect(jsonPath("$.activities[0].type").value("PUSH"))
                .andExpect(jsonPath("$.activities[0].repoName").value("AmitKumar1404/portfolio"))
                .andExpect(jsonPath("$.activities[0].repoUrl").value("https://github.com/AmitKumar1404/portfolio"))
                .andExpect(jsonPath("$.activities[0].message").value("Update portfolio project"))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.cacheKey").doesNotExist())
                .andExpect(jsonPath("$.payload").doesNotExist())
                .andExpect(jsonPath("$.expiresAt").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.activities[0].payload").doesNotExist())
                .andExpect(jsonPath("$.activities[0].actor").doesNotExist());
    }

    @Test
    void githubFailureWithoutCacheReturns503() throws Exception {
        when(githubCacheRepository.findByCacheKey(any())).thenReturn(Optional.empty());
        when(githubClient.fetchPublicEvents()).thenThrow(new GithubUpstreamException("down"));

        mockMvc.perform(get("/api/v1/github/activity"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value("GITHUB_UNAVAILABLE"))
                .andExpect(jsonPath("$.message").value("GitHub activity is temporarily unavailable"));
    }
}
