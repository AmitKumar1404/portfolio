package dev.amitkumar.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import dev.amitkumar.portfolio.contact.ContactMessageRepository;
import dev.amitkumar.portfolio.experience.ExperienceRepository;
import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;
import dev.amitkumar.portfolio.skill.SkillRepository;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioApplicationTests {

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
    void contextLoads() {
        // Security, CORS, exception handling, and actuator must wire without a database.
    }
}
