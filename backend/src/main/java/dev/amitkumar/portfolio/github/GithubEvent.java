package dev.amitkumar.portfolio.github;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
record GithubEvent(
        String type,
        GithubEventRepo repo,
        JsonNode payload,
        @JsonProperty("created_at") Instant createdAt) {
}
