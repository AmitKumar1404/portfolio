package dev.amitkumar.portfolio.experience;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

final class ExperienceFixtures {

    private ExperienceFixtures() {
    }

    static Experience currentRole() {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new Experience(
                1L,
                "REPLACE_ME: Company",
                "REPLACE_ME: Role",
                EmploymentType.FULL_TIME,
                "REPLACE_ME: Location",
                null,
                LocalDate.of(2024, 1, 1),
                null,
                true,
                "REPLACE_ME: Experience description",
                1,
                now,
                now,
                List.of(
                        new ExperienceHighlight(10L, "REPLACE_ME: Achievement or responsibility", 1, now),
                        new ExperienceHighlight(11L, "REPLACE_ME: Achievement or responsibility", 2, now)));
    }

    static Experience previousRole() {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new Experience(
                2L,
                "REPLACE_ME: Previous Company",
                "REPLACE_ME: Previous Role",
                EmploymentType.CONTRACT,
                "REPLACE_ME: Location",
                null,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2023, 12, 31),
                false,
                "REPLACE_ME: Previous experience description",
                2,
                now,
                now,
                List.of(new ExperienceHighlight(12L, "REPLACE_ME: Achievement or responsibility", 1, now)));
    }
}
