package dev.amitkumar.portfolio.github;

import java.util.List;

public record GithubActivityResponse(
        String username,
        String profileUrl,
        List<GithubActivityItemResponse> activities,
        String source,
        boolean cached,
        boolean stale) {

    static GithubActivityResponse from(GithubActivitySnapshot snapshot, boolean cached, boolean stale) {
        return new GithubActivityResponse(
                snapshot.username(),
                snapshot.profileUrl(),
                snapshot.activities(),
                snapshot.source(),
                cached,
                stale);
    }
}
