package dev.amitkumar.portfolio.github;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

final class GithubFixtures {

    static final Instant CREATED_AT = Instant.parse("2026-09-05T12:00:00Z");

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private GithubFixtures() {
    }

    static GithubProperties properties(int maxActivities) {
        return new GithubProperties(
                "AmitKumar1404",
                "https://api.github.com",
                Duration.ofSeconds(3),
                Duration.ofSeconds(5),
                Duration.ofMinutes(15),
                maxActivities);
    }

    static GithubEvent pushEvent() {
        ObjectNode payload = MAPPER.createObjectNode();
        ArrayNode commits = payload.putArray("commits");
        commits.addObject().put("message", "Update portfolio project\n\nDetails");
        payload.put("size", 1);
        return new GithubEvent("PushEvent", repo(), payload, CREATED_AT);
    }

    static GithubEvent pullRequestEvent() {
        ObjectNode payload = MAPPER.createObjectNode();
        payload.put("action", "opened");
        payload.putObject("pull_request").put("title", "Add activity API").put("number", 12);
        return new GithubEvent("PullRequestEvent", repo(), payload, CREATED_AT.minusSeconds(60));
    }

    static GithubEvent deleteEvent() {
        return new GithubEvent("DeleteEvent", repo(), MAPPER.createObjectNode(), CREATED_AT.minusSeconds(120));
    }

    static GithubEventRepo repo() {
        return new GithubEventRepo("AmitKumar1404/portfolio", "https://api.github.com/repos/AmitKumar1404/portfolio");
    }

    static GithubActivitySnapshot snapshot() {
        return new GithubActivitySnapshot(
                "AmitKumar1404",
                "https://github.com/AmitKumar1404",
                List.of(new GithubActivityItemResponse(
                        GithubActivityType.PUSH,
                        "AmitKumar1404/portfolio",
                        "https://github.com/AmitKumar1404/portfolio",
                        "Update portfolio project",
                        CREATED_AT)),
                "github");
    }

    static GithubCache freshCache() {
        return new GithubCache(
                1L,
                "activity:AmitKumar1404",
                snapshot(),
                Instant.now().plus(Duration.ofMinutes(10)),
                CREATED_AT,
                CREATED_AT);
    }

    static GithubCache staleCache() {
        return new GithubCache(
                1L,
                "activity:AmitKumar1404",
                snapshot(),
                Instant.now().minus(Duration.ofMinutes(1)),
                CREATED_AT,
                CREATED_AT);
    }

    static String eventsJson() {
        return """
                [
                  {
                    "type": "PushEvent",
                    "repo": {
                      "name": "AmitKumar1404/portfolio",
                      "url": "https://api.github.com/repos/AmitKumar1404/portfolio"
                    },
                    "payload": {
                      "size": 1,
                      "commits": [
                        { "message": "Update portfolio project" }
                      ]
                    },
                    "created_at": "2026-09-05T12:00:00Z"
                  }
                ]
                """;
    }
}
