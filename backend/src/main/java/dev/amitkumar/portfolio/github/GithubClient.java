package dev.amitkumar.portfolio.github;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public class GithubClient {

    private static final Logger log = LoggerFactory.getLogger(GithubClient.class);

    private static final ParameterizedTypeReference<List<GithubEvent>> EVENT_LIST =
            new ParameterizedTypeReference<>() {
            };

    private final GithubProperties properties;
    private final RestClient restClient;

    GithubClient(GithubProperties properties, RestClient restClient) {
        this.properties = properties;
        this.restClient = restClient;
    }

    List<GithubEvent> fetchPublicEvents() {
        try {
            List<GithubEvent> events = restClient.get()
                    .uri("/users/{username}/events/public?per_page=30", properties.username())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        int status = response.getStatusCode().value();
                        log.warn("GitHub public events request failed status={}", status);
                        throw new GithubUpstreamException("GitHub returned status " + status);
                    })
                    .body(EVENT_LIST);
            return events == null ? List.of() : List.copyOf(events);
        } catch (GithubUpstreamException ex) {
            throw ex;
        } catch (RestClientException ex) {
            log.warn("GitHub public events request failed: {}", ex.getClass().getSimpleName());
            throw new GithubUpstreamException("GitHub request failed", ex);
        }
    }
}
