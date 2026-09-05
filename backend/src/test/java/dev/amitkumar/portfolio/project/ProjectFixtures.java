package dev.amitkumar.portfolio.project;

import java.time.Instant;

final class ProjectFixtures {

    private ProjectFixtures() {
    }

    static Project chatbot() {
        return project(
                1L,
                "chatbot-rag-platform",
                "Chatbot RAG Platform",
                "Portfolio RAG chatbot.",
                true,
                true,
                1,
                null,
                null);
    }

    static Project booking() {
        return project(
                2L,
                "local-service-booking",
                "Local Service Booking",
                "Portfolio booking flow.",
                false,
                true,
                2,
                null,
                null);
    }

    static Project portfolio() {
        return project(
                3L,
                "developer-portfolio",
                "Developer Portfolio",
                "This site.",
                false,
                true,
                3,
                "https://github.com/AmitKumar1404/portfolio",
                null);
    }

    private static Project project(
            Long id,
            String slug,
            String title,
            String shortDescription,
            boolean featured,
            boolean published,
            int sortOrder,
            String githubUrl,
            String liveUrl) {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new Project(
                id,
                slug,
                title,
                null,
                shortDescription,
                null,
                null,
                ProjectStatus.WIP,
                featured,
                published,
                githubUrl,
                liveUrl,
                null,
                null,
                null,
                sortOrder,
                now,
                now);
    }
}
