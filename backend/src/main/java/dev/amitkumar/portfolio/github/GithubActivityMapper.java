package dev.amitkumar.portfolio.github;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

final class GithubActivityMapper {

    private static final int MESSAGE_MAX = 200;

    private final GithubProperties properties;

    GithubActivityMapper(GithubProperties properties) {
        this.properties = properties;
    }

    GithubActivitySnapshot toSnapshot(List<GithubEvent> events) {
        List<GithubActivityItemResponse> activities = events.stream()
                .map(this::toItem)
                .flatMap(Optional::stream)
                .sorted(Comparator
                        .comparing(GithubActivityItemResponse::createdAt)
                        .reversed()
                        .thenComparing(GithubActivityItemResponse::repoName))
                .limit(properties.maxActivities())
                .toList();
        String username = properties.username();
        return new GithubActivitySnapshot(
                username,
                "https://github.com/" + username,
                activities,
                "github");
    }

    private Optional<GithubActivityItemResponse> toItem(GithubEvent event) {
        if (event == null || event.type() == null || event.createdAt() == null) {
            return Optional.empty();
        }
        GithubActivityType type = toType(event.type());
        if (type == null) {
            return Optional.empty();
        }
        if (event.repo() == null || event.repo().name() == null || event.repo().name().isBlank()) {
            return Optional.empty();
        }
        String repoName = event.repo().name();
        return Optional.of(new GithubActivityItemResponse(
                type,
                repoName,
                "https://github.com/" + repoName,
                truncate(messageFor(type, event)),
                event.createdAt()));
    }

    private static GithubActivityType toType(String githubType) {
        return switch (githubType) {
            case "PushEvent" -> GithubActivityType.PUSH;
            case "PullRequestEvent" -> GithubActivityType.PULL_REQUEST;
            case "IssuesEvent" -> GithubActivityType.ISSUE;
            case "IssueCommentEvent" -> GithubActivityType.COMMENT;
            case "CreateEvent" -> GithubActivityType.CREATE;
            case "ReleaseEvent" -> GithubActivityType.RELEASE;
            case "ForkEvent" -> GithubActivityType.FORK;
            case "WatchEvent" -> GithubActivityType.STAR;
            default -> null;
        };
    }

    private static String messageFor(GithubActivityType type, GithubEvent event) {
        JsonNode payload = event.payload();
        return switch (type) {
            case PUSH -> pushMessage(payload);
            case PULL_REQUEST -> actionTitle(payload, "pull_request", "Updated a pull request");
            case ISSUE -> actionTitle(payload, "issue", "Updated an issue");
            case COMMENT -> commentMessage(payload);
            case CREATE -> createMessage(payload);
            case RELEASE -> releaseMessage(payload);
            case FORK -> "Forked " + event.repo().name();
            case STAR -> "Starred " + event.repo().name();
            case OTHER -> "GitHub activity";
        };
    }

    private static String pushMessage(JsonNode payload) {
        if (payload != null) {
            JsonNode commits = payload.path("commits");
            if (commits.isArray() && !commits.isEmpty()) {
                String message = firstLine(commits.get(0).path("message").asText(""));
                if (!message.isBlank()) {
                    return message;
                }
            }
            int size = payload.path("size").asInt(0);
            if (size > 1) {
                return "Pushed " + size + " commits";
            }
        }
        return "Pushed a commit";
    }

    private static String actionTitle(JsonNode payload, String objectField, String fallback) {
        if (payload == null) {
            return fallback;
        }
        String action = payload.path("action").asText("");
        String title = payload.path(objectField).path("title").asText("");
        int number = payload.path(objectField).path("number").asInt(0);
        if (!title.isBlank() && number > 0) {
            return (action.isBlank() ? "Updated" : capitalize(action)) + " #" + number + ": " + title;
        }
        if (!title.isBlank()) {
            return (action.isBlank() ? "Updated" : capitalize(action)) + ": " + title;
        }
        return fallback;
    }

    private static String commentMessage(JsonNode payload) {
        if (payload == null) {
            return "Commented on an issue";
        }
        int number = payload.path("issue").path("number").asInt(0);
        String title = payload.path("issue").path("title").asText("");
        if (number > 0 && !title.isBlank()) {
            return "Commented on #" + number + ": " + title;
        }
        String body = firstLine(payload.path("comment").path("body").asText(""));
        if (!body.isBlank()) {
            return body;
        }
        return "Commented on an issue";
    }

    private static String createMessage(JsonNode payload) {
        if (payload == null) {
            return "Created a repository resource";
        }
        String refType = payload.path("ref_type").asText("repository");
        String ref = payload.path("ref").asText("");
        if (!ref.isBlank()) {
            return "Created " + refType + " " + ref;
        }
        return "Created " + refType;
    }

    private static String releaseMessage(JsonNode payload) {
        if (payload == null) {
            return "Published a release";
        }
        String name = payload.path("release").path("name").asText("");
        if (name.isBlank()) {
            name = payload.path("release").path("tag_name").asText("");
        }
        if (!name.isBlank()) {
            return "Released " + name;
        }
        return "Published a release";
    }

    private static String firstLine(String value) {
        int newline = value.indexOf('\n');
        return newline >= 0 ? value.substring(0, newline).trim() : value.trim();
    }

    private static String capitalize(String value) {
        if (value.isBlank()) {
            return value;
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    private static String truncate(String value) {
        if (value.length() <= MESSAGE_MAX) {
            return value;
        }
        return value.substring(0, MESSAGE_MAX - 1) + "…";
    }
}
