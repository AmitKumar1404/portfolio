package dev.amitkumar.portfolio.profile;

import java.time.Instant;

final class ProfileFixtures {

    private ProfileFixtures() {
    }

    static Profile amitKumar() {
        Instant now = Instant.parse("2026-09-03T12:00:00Z");
        return new Profile(
                1L,
                "Amit Kumar",
                "Software Engineer",
                "Short bio",
                "Long bio",
                "Bengaluru",
                "amit@example.com",
                "https://github.com/AmitKumar1404",
                "https://linkedin.com/in/example",
                "https://example.com",
                "https://example.com/resume.pdf",
                "https://example.com/avatar.jpg",
                Availability.SELECTIVE,
                now,
                now);
    }
}
