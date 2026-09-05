package dev.amitkumar.portfolio.github;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class GithubClientTest {

    private MockRestServiceServer server;
    private GithubClient githubClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .defaultHeader(HttpHeaders.USER_AGENT, "amitkumar-portfolio-api");
        server = MockRestServiceServer.bindTo(builder).build();
        githubClient = new GithubClient(GithubFixtures.properties(15), builder.build());
    }

    @Test
    void successfulResponseParsesEvents() {
        server.expect(requestTo("https://api.github.com/users/AmitKumar1404/events/public?per_page=30"))
                .andExpect(header(HttpHeaders.ACCEPT, "application/vnd.github+json"))
                .andRespond(withSuccess(GithubFixtures.eventsJson(), MediaType.APPLICATION_JSON));

        List<GithubEvent> events = githubClient.fetchPublicEvents();

        assertThat(events).hasSize(1);
        assertThat(events.getFirst().type()).isEqualTo("PushEvent");
        assertThat(events.getFirst().repo().name()).isEqualTo("AmitKumar1404/portfolio");
        server.verify();
    }

    @Test
    void non2xxResponseThrowsUpstreamException() {
        server.expect(requestTo("https://api.github.com/users/AmitKumar1404/events/public?per_page=30"))
                .andRespond(withStatus(HttpStatus.FORBIDDEN).body("{\"message\":\"rate limited\"}"));

        assertThatThrownBy(githubClient::fetchPublicEvents)
                .isInstanceOf(GithubUpstreamException.class)
                .hasMessageContaining("403");
        server.verify();
    }

    @Test
    void serverErrorThrowsUpstreamException() {
        server.expect(requestTo("https://api.github.com/users/AmitKumar1404/events/public?per_page=30"))
                .andRespond(withServerError());

        assertThatThrownBy(githubClient::fetchPublicEvents).isInstanceOf(GithubUpstreamException.class);
        server.verify();
    }

    @Test
    void malformedJsonThrowsUpstreamException() {
        server.expect(requestTo("https://api.github.com/users/AmitKumar1404/events/public?per_page=30"))
                .andRespond(withSuccess("{", MediaType.APPLICATION_JSON));

        assertThatThrownBy(githubClient::fetchPublicEvents).isInstanceOf(GithubUpstreamException.class);
        server.verify();
    }

    @Test
    void connectionFailureThrowsUpstreamException() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(200));
        factory.setReadTimeout(Duration.ofMillis(200));
        GithubClient unreachable = new GithubClient(
                GithubFixtures.properties(15),
                RestClient.builder()
                        .baseUrl("http://127.0.0.1:1")
                        .requestFactory(factory)
                        .build());

        assertThatThrownBy(unreachable::fetchPublicEvents).isInstanceOf(GithubUpstreamException.class);
    }
}
