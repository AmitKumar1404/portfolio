package dev.amitkumar.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioApplicationTests {

    @Test
    void contextLoads() {
        // Security, CORS, exception handling, and actuator must wire without a database.
    }
}
