package dev.amitkumar.portfolio.project;

import static org.hamcrest.Matchers.nullValue;
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
import dev.amitkumar.portfolio.github.GithubCacheRepository;
import dev.amitkumar.portfolio.experience.ExperienceRepository;
import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.skill.SkillRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectRepository projectRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private ExperienceRepository experienceRepository;

    @MockitoBean
    private SkillRepository skillRepository;

    @MockitoBean
    private ContactMessageRepository contactMessageRepository;

    @MockitoBean
    private GithubCacheRepository githubCacheRepository;

    @Test
    void getProjectsReturns200InDisplayOrder() throws Exception {
        when(projectRepository.findAllByPublishedTrueOrderBySortOrderAsc())
                .thenReturn(List.of(ProjectFixtures.chatbot(), ProjectFixtures.booking(), ProjectFixtures.portfolio()));

        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("chatbot-rag-platform"))
                .andExpect(jsonPath("$[0].title").value("Chatbot RAG Platform"))
                .andExpect(jsonPath("$[0].featured").value(true))
                .andExpect(jsonPath("$[0].displayOrder").value(1))
                .andExpect(jsonPath("$[1].slug").value("local-service-booking"))
                .andExpect(jsonPath("$[1].displayOrder").value(2))
                .andExpect(jsonPath("$[2].slug").value("developer-portfolio"))
                .andExpect(jsonPath("$[2].githubUrl").value("https://github.com/AmitKumar1404/portfolio"))
                .andExpect(jsonPath("$[2].liveUrl").value(nullValue()))
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getProjectsWhenEmptyReturns200AndEmptyArray() throws Exception {
        when(projectRepository.findAllByPublishedTrueOrderBySortOrderAsc()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getProjectBySlugWhenPresentReturns200() throws Exception {
        when(projectRepository.findBySlugAndPublishedTrue("chatbot-rag-platform"))
                .thenReturn(Optional.of(ProjectFixtures.chatbot()));

        mockMvc.perform(get("/api/v1/projects/chatbot-rag-platform"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("chatbot-rag-platform"))
                .andExpect(jsonPath("$.title").value("Chatbot RAG Platform"))
                .andExpect(jsonPath("$.shortDescription").value("Portfolio RAG chatbot."))
                .andExpect(jsonPath("$.featured").value(true))
                .andExpect(jsonPath("$.displayOrder").value(1))
                .andExpect(jsonPath("$.githubUrl").value(nullValue()))
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    void getProjectBySlugWhenMissingReturns404() throws Exception {
        when(projectRepository.findBySlugAndPublishedTrue("missing")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/projects/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value("PROJECT_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Project not found"))
                .andExpect(jsonPath("$.path").value("/api/v1/projects/missing"));
    }
}
