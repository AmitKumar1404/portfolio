package dev.amitkumar.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import dev.amitkumar.portfolio.profile.ProfileRepository;
import dev.amitkumar.portfolio.project.ProjectRepository;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioApplicationTests {

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private ProjectRepository projectRepository;

    @Test
    void contextLoads() {
        // Security, CORS, exception handling, and actuator must wire without a database.
    }
}
