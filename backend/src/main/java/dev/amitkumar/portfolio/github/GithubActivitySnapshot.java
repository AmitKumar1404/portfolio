package dev.amitkumar.portfolio.github;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubActivitySnapshot(
        String username,
        String profileUrl,
        List<GithubActivityItemResponse> activities,
        String source) {
}
