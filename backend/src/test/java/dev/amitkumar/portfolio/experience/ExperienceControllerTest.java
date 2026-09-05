package dev.amitkumar.portfolio.experience;

import static org.hamcrest.Matchers.nullValue;
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

import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;
import dev.amitkumar.portfolio.skill.SkillRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExperienceRepository experienceRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private ProjectRepository projectRepository;

    @MockitoBean
    private SkillRepository skillRepository;

    @Test
    void getExperiencesReturns200WithPublicFields() throws Exception {
        when(experienceRepository.findAllByOrderBySortOrderAscIdAsc())
                .thenReturn(List.of(ExperienceFixtures.currentRole(), ExperienceFixtures.previousRole()));

        mockMvc.perform(get("/api/v1/experiences"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].company").value("REPLACE_ME: Company"))
                .andExpect(jsonPath("$[0].role").value("REPLACE_ME: Role"))
                .andExpect(jsonPath("$[0].employmentType").value("FULL_TIME"))
                .andExpect(jsonPath("$[0].location").value("REPLACE_ME: Location"))
                .andExpect(jsonPath("$[0].startDate").value("2024-01-01"))
                .andExpect(jsonPath("$[0].endDate").value(nullValue()))
                .andExpect(jsonPath("$[0].current").value(true))
                .andExpect(jsonPath("$[0].description").value("REPLACE_ME: Experience description"))
                .andExpect(jsonPath("$[0].highlights.length()").value(2))
                .andExpect(jsonPath("$[0].displayOrder").value(1))
                .andExpect(jsonPath("$[1].company").value("REPLACE_ME: Previous Company"))
                .andExpect(jsonPath("$[1].displayOrder").value(2))
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].createdAt").doesNotExist())
                .andExpect(jsonPath("$[0].updatedAt").doesNotExist());
    }

    @Test
    void getExperiencesWhenEmptyReturns200AndEmptyArray() throws Exception {
        when(experienceRepository.findAllByOrderBySortOrderAscIdAsc()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/experiences"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
