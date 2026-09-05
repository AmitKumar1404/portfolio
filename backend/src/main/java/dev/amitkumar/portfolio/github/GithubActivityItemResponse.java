package dev.amitkumar.portfolio.github;

import java.time.Instant;

public record GithubActivityItemResponse(
        GithubActivityType type,
        String repoName,
        String repoUrl,
        String message,
        Instant createdAt) {
}
