package dev.amitkumar.portfolio.github;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.github")
public record GithubProperties(
        String username,
        String apiBaseUrl,
        Duration connectTimeout,
        Duration readTimeout,
        Duration cacheTtl,
        int maxActivities) {

    public GithubProperties {
        if (username == null || username.isBlank()) {
            username = "AmitKumar1404";
        }
        if (apiBaseUrl == null || apiBaseUrl.isBlank()) {
            apiBaseUrl = "https://api.github.com";
        }
        if (connectTimeout == null) {
            connectTimeout = Duration.ofSeconds(3);
        }
        if (readTimeout == null) {
            readTimeout = Duration.ofSeconds(5);
        }
        if (cacheTtl == null) {
            cacheTtl = Duration.ofMinutes(15);
        }
        if (maxActivities <= 0) {
            maxActivities = 15;
        }
    }
}
