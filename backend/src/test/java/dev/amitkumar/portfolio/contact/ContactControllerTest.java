package dev.amitkumar.portfolio.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.amitkumar.portfolio.github.GithubCacheRepository;
import dev.amitkumar.portfolio.experience.ExperienceRepository;
import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;
import dev.amitkumar.portfolio.skill.SkillRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactMessageRepository contactMessageRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private ProjectRepository projectRepository;

    @MockitoBean
    private ExperienceRepository experienceRepository;

    @MockitoBean
    private SkillRepository skillRepository;

    @MockitoBean
    private GithubCacheRepository githubCacheRepository;

    @Test
    void validAnonymousSubmissionReturns201AndCallsService() throws Exception {
        when(contactMessageRepository.save(any(ContactMessage.class))).thenReturn(ContactFixtures.saved());

        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ContactFixtures.validJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(42))
                .andExpect(jsonPath("$.status").doesNotExist())
                .andExpect(jsonPath("$.ipHash").doesNotExist())
                .andExpect(jsonPath("$.userAgent").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.updatedAt").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.subject").doesNotExist());

        ArgumentCaptor<ContactMessage> captor = ArgumentCaptor.forClass(ContactMessage.class);
        verify(contactMessageRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Test Visitor");
        assertThat(captor.getValue().getEmail()).isEqualTo("visitor@example.com");
        assertThat(captor.getValue().getSubject()).isEqualTo("Portfolio Contact Test");
        assertThat(captor.getValue().getMessage()).isEqualTo("This is a test contact message.");
        assertThat(captor.getValue().getStatus()).isEqualTo(ContactMessageStatus.NEW);
    }

    @Test
    void missingNameReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "visitor@example.com",
                                  "subject": "Portfolio Contact Test",
                                  "message": "This is a test contact message."
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.name").exists());
    }

    @Test
    void invalidEmailReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Test Visitor",
                                  "email": "not-an-email",
                                  "subject": "Portfolio Contact Test",
                                  "message": "This is a test contact message."
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.email").exists());
    }

    @Test
    void missingSubjectReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Test Visitor",
                                  "email": "visitor@example.com",
                                  "message": "This is a test contact message."
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.subject").exists());
    }

    @Test
    void missingMessageReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Test Visitor",
                                  "email": "visitor@example.com",
                                  "subject": "Portfolio Contact Test"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.message").exists());
    }

    @Test
    void oversizedNameReturns400() throws Exception {
        String name = "N".repeat(81);
        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "email": "visitor@example.com",
                                  "subject": "Portfolio Contact Test",
                                  "message": "This is a test contact message."
                                }
                                """.formatted(name)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.name").exists());
    }

    @Test
    void oversizedSubjectReturns400() throws Exception {
        String subject = "S".repeat(121);
        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Test Visitor",
                                  "email": "visitor@example.com",
                                  "subject": "%s",
                                  "message": "This is a test contact message."
                                }
                                """.formatted(subject)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.fieldErrors.subject").exists());
    }
}
