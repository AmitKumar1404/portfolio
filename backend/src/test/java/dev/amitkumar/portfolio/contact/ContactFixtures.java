package dev.amitkumar.portfolio.contact;

import java.time.Instant;

final class ContactFixtures {

    private ContactFixtures() {
    }

    static ContactRequest validRequest() {
        return new ContactRequest(
                "Test Visitor",
                "visitor@example.com",
                "Portfolio Contact Test",
                "This is a test contact message.");
    }

    static ContactMessage saved() {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new ContactMessage(
                42L,
                "Test Visitor",
                "visitor@example.com",
                "Portfolio Contact Test",
                "This is a test contact message.",
                ContactMessageStatus.NEW,
                null,
                null,
                now,
                now);
    }

    static String validJson() {
        return """
                {
                  "name": "Test Visitor",
                  "email": "visitor@example.com",
                  "subject": "Portfolio Contact Test",
                  "message": "This is a test contact message."
                }
                """;
    }
}
