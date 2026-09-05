package dev.amitkumar.portfolio.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.amitkumar.portfolio.contact.ContactMessageRepository;
import dev.amitkumar.portfolio.github.GithubActivityResponse;
import dev.amitkumar.portfolio.github.GithubActivityService;
import dev.amitkumar.portfolio.github.GithubCacheRepository;
import dev.amitkumar.portfolio.contact.ContactRequest;
import dev.amitkumar.portfolio.contact.ContactResponse;
import dev.amitkumar.portfolio.contact.ContactService;
import dev.amitkumar.portfolio.experience.ExperienceRepository;
import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;
import dev.amitkumar.portfolio.skill.SkillRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

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

    @MockitoBean
    private ContactService contactService;

    @MockitoBean
    private GithubCacheRepository githubCacheRepository;

    @MockitoBean
    private GithubActivityService githubActivityService;

    @Test
    void healthIsPublicAndUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void unmappedAdminPathIsUnauthorized() throws Exception {
        mockMvc.perform(get("/admin/projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void experiencesArePublic() throws Exception {
        mockMvc.perform(get("/api/v1/experiences"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void skillsArePublic() throws Exception {
        mockMvc.perform(get("/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void contactSubmissionIsPublic() throws Exception {
        when(contactService.submit(any(ContactRequest.class))).thenReturn(new ContactResponse(42L));

        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Test Visitor",
                                  "email": "visitor@example.com",
                                  "subject": "Portfolio Contact Test",
                                  "message": "This is a test contact message."
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(42));
    }

    @Test
    void githubActivityIsPublic() throws Exception {
        when(githubActivityService.getActivity()).thenReturn(new GithubActivityResponse(
                "AmitKumar1404",
                "https://github.com/AmitKumar1404",
                List.of(),
                "github",
                true,
                false));

        mockMvc.perform(get("/api/v1/github/activity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("AmitKumar1404"));
    }
}
