package dev.amitkumar.portfolio.github;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

class GithubActivityMapperTest {

    private final GithubActivityMapper mapper = new GithubActivityMapper(GithubFixtures.properties(2));

    @Test
    void mapsSupportedEventsAndIgnoresUnsupportedTypes() {
        GithubActivitySnapshot snapshot = mapper.toSnapshot(
                List.of(GithubFixtures.pushEvent(), GithubFixtures.deleteEvent(), GithubFixtures.pullRequestEvent()));

        assertThat(snapshot.username()).isEqualTo("AmitKumar1404");
        assertThat(snapshot.profileUrl()).isEqualTo("https://github.com/AmitKumar1404");
        assertThat(snapshot.source()).isEqualTo("github");
        assertThat(snapshot.activities()).hasSize(2);
        assertThat(snapshot.activities().getFirst().type()).isEqualTo(GithubActivityType.PUSH);
        assertThat(snapshot.activities().getFirst().message()).isEqualTo("Update portfolio project");
        assertThat(snapshot.activities().getFirst().repoUrl()).isEqualTo("https://github.com/AmitKumar1404/portfolio");
        assertThat(snapshot.activities().get(1).type()).isEqualTo(GithubActivityType.PULL_REQUEST);
        assertThat(snapshot.activities().get(1).message()).isEqualTo("Opened #12: Add activity API");
        assertThat(snapshot.activities())
                .extracting(GithubActivityItemResponse::repoName)
                .doesNotContain("https://api.github.com/repos/AmitKumar1404/portfolio");
    }

    @Test
    void ordersActivitiesNewestFirstBeforeBounding() {
        GithubActivityMapper unlimited = new GithubActivityMapper(GithubFixtures.properties(10));
        GithubEvent older = new GithubEvent(
                "WatchEvent",
                GithubFixtures.repo(),
                new ObjectMapper().createObjectNode(),
                GithubFixtures.CREATED_AT.minusSeconds(120));
        GithubEvent newer = new GithubEvent(
                "ForkEvent",
                GithubFixtures.repo(),
                new ObjectMapper().createObjectNode(),
                GithubFixtures.CREATED_AT);

        GithubActivitySnapshot snapshot = unlimited.toSnapshot(List.of(older, newer));

        assertThat(snapshot.activities())
                .extracting(GithubActivityItemResponse::type)
                .containsExactly(GithubActivityType.FORK, GithubActivityType.STAR);
        assertThat(snapshot.activities().getFirst().createdAt()).isAfter(snapshot.activities().get(1).createdAt());
    }

    @Test
    void boundsActivityListToConfiguredMaximum() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<GithubEvent> events = IntStream.range(0, 5)
                .mapToObj(index -> new GithubEvent(
                        "WatchEvent",
                        GithubFixtures.repo(),
                        objectMapper.createObjectNode(),
                        Instant.parse("2026-09-05T12:00:00Z").minusSeconds(index)))
                .toList();

        GithubActivitySnapshot snapshot = mapper.toSnapshot(events);

        assertThat(snapshot.activities()).hasSize(2);
        assertThat(snapshot.activities()).allMatch(item -> item.type() == GithubActivityType.STAR);
    }

    @Test
    void mapsRemainingSupportedEventTypes() {
        ObjectMapper objectMapper = new ObjectMapper();
        GithubActivityMapper unlimited = new GithubActivityMapper(GithubFixtures.properties(10));

        ObjectNode issue = objectMapper.createObjectNode();
        issue.put("action", "opened");
        issue.putObject("issue").put("title", "Bug").put("number", 3);

        ObjectNode comment = objectMapper.createObjectNode();
        comment.putObject("issue").put("title", "Bug").put("number", 3);
        comment.putObject("comment").put("body", "Looks good");

        ObjectNode create = objectMapper.createObjectNode();
        create.put("ref_type", "branch");
        create.put("ref", "feature/domain-apis");

        ObjectNode release = objectMapper.createObjectNode();
        release.putObject("release").put("name", "v1.0.0");

        GithubActivitySnapshot snapshot = unlimited.toSnapshot(List.of(
                new GithubEvent("IssuesEvent", GithubFixtures.repo(), issue, GithubFixtures.CREATED_AT),
                new GithubEvent("IssueCommentEvent", GithubFixtures.repo(), comment, GithubFixtures.CREATED_AT),
                new GithubEvent("CreateEvent", GithubFixtures.repo(), create, GithubFixtures.CREATED_AT),
                new GithubEvent("ReleaseEvent", GithubFixtures.repo(), release, GithubFixtures.CREATED_AT),
                new GithubEvent("ForkEvent", GithubFixtures.repo(), objectMapper.createObjectNode(), GithubFixtures.CREATED_AT),
                new GithubEvent("WatchEvent", GithubFixtures.repo(), objectMapper.createObjectNode(), GithubFixtures.CREATED_AT)));

        assertThat(snapshot.activities())
                .extracting(GithubActivityItemResponse::type)
                .containsExactly(
                        GithubActivityType.ISSUE,
                        GithubActivityType.COMMENT,
                        GithubActivityType.CREATE,
                        GithubActivityType.RELEASE,
                        GithubActivityType.FORK,
                        GithubActivityType.STAR);
        assertThat(snapshot.activities())
                .extracting(GithubActivityItemResponse::message)
                .containsExactly(
                        "Opened #3: Bug",
                        "Commented on #3: Bug",
                        "Created branch feature/domain-apis",
                        "Released v1.0.0",
                        "Forked AmitKumar1404/portfolio",
                        "Starred AmitKumar1404/portfolio");
    }
}
