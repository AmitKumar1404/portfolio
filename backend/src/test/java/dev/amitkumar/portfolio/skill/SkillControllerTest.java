package dev.amitkumar.portfolio.skill;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import dev.amitkumar.portfolio.experience.ExperienceRepository;
import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillRepository skillRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private ProjectRepository projectRepository;

    @MockitoBean
    private ExperienceRepository experienceRepository;

    @Test
    void getSkillsReturns200GroupedByCategory() throws Exception {
        when(skillRepository.findAllPublicOrdered())
                .thenReturn(List.of(SkillFixtures.java(), SkillFixtures.springBoot(), SkillFixtures.react()));

        mockMvc.perform(get("/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].category").value("REPLACE_ME: Backend"))
                .andExpect(jsonPath("$[0].slug").value("backend"))
                .andExpect(jsonPath("$[0].displayOrder").value(1))
                .andExpect(jsonPath("$[0].skills[0]").value("REPLACE_ME: Java"))
                .andExpect(jsonPath("$[0].skills[1]").value("REPLACE_ME: Spring Boot"))
                .andExpect(jsonPath("$[1].slug").value("frontend"))
                .andExpect(jsonPath("$[1].skills[0]").value("REPLACE_ME: React"))
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].createdAt").doesNotExist())
                .andExpect(jsonPath("$[0].updatedAt").doesNotExist())
                .andExpect(jsonPath("$[0].level").doesNotExist())
                .andExpect(jsonPath("$[0].years").doesNotExist());
    }

    @Test
    void getSkillsWhenEmptyReturns200AndEmptyArray() throws Exception {
        when(skillRepository.findAllPublicOrdered()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
