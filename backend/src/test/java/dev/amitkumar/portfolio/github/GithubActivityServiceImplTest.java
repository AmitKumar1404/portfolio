package dev.amitkumar.portfolio.github;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GithubActivityServiceImplTest {

    @Mock
    private GithubClient githubClient;

    @Mock
    private GithubCacheRepository githubCacheRepository;

    private GithubActivityServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new GithubActivityServiceImpl(githubClient, githubCacheRepository, GithubFixtures.properties(15));
    }

    @Test
    void freshCacheDoesNotCallGithub() {
        when(githubCacheRepository.findByCacheKey("activity:AmitKumar1404"))
                .thenReturn(Optional.of(GithubFixtures.freshCache()));

        GithubActivityResponse response = service.getActivity();

        assertThat(response.cached()).isTrue();
        assertThat(response.stale()).isFalse();
        assertThat(response.username()).isEqualTo("AmitKumar1404");
        assertThat(response.activities()).hasSize(1);
        verify(githubClient, never()).fetchPublicEvents();
        verify(githubCacheRepository, never()).save(any());
    }

    @Test
    void missingCacheCallsGithubAndStoresSnapshot() {
        when(githubCacheRepository.findByCacheKey("activity:AmitKumar1404")).thenReturn(Optional.empty());
        when(githubClient.fetchPublicEvents()).thenReturn(List.of(GithubFixtures.pushEvent()));

        GithubActivityResponse response = service.getActivity();

        assertThat(response.cached()).isFalse();
        assertThat(response.stale()).isFalse();
        assertThat(response.source()).isEqualTo("github");
        assertThat(response.activities().getFirst().type()).isEqualTo(GithubActivityType.PUSH);
        assertThat(response.activities().getFirst().message()).isEqualTo("Update portfolio project");

        ArgumentCaptor<GithubCache> captor = ArgumentCaptor.forClass(GithubCache.class);
        verify(githubCacheRepository).save(captor.capture());
        assertThat(captor.getValue().getCacheKey()).isEqualTo("activity:AmitKumar1404");
        assertThat(captor.getValue().getPayload().activities()).hasSize(1);
    }

    @Test
    void staleCacheCallsGithub() {
        when(githubCacheRepository.findByCacheKey("activity:AmitKumar1404"))
                .thenReturn(Optional.of(GithubFixtures.staleCache()));
        when(githubClient.fetchPublicEvents()).thenReturn(List.of(GithubFixtures.pullRequestEvent()));

        GithubActivityResponse response = service.getActivity();

        assertThat(response.cached()).isFalse();
        assertThat(response.activities().getFirst().type()).isEqualTo(GithubActivityType.PULL_REQUEST);
        verify(githubClient).fetchPublicEvents();
        verify(githubCacheRepository).save(any(GithubCache.class));
    }

    @Test
    void githubFailureWithStaleCacheReturnsStaleData() {
        when(githubCacheRepository.findByCacheKey("activity:AmitKumar1404"))
                .thenReturn(Optional.of(GithubFixtures.staleCache()));
        when(githubClient.fetchPublicEvents()).thenThrow(new GithubUpstreamException("rate limited"));

        GithubActivityResponse response = service.getActivity();

        assertThat(response.cached()).isTrue();
        assertThat(response.stale()).isTrue();
        assertThat(response.activities().getFirst().message()).isEqualTo("Update portfolio project");
        verify(githubCacheRepository, never()).save(any());
    }

    @Test
    void githubFailureWithoutCacheThrowsControlledException() {
        when(githubCacheRepository.findByCacheKey("activity:AmitKumar1404")).thenReturn(Optional.empty());
        when(githubClient.fetchPublicEvents()).thenThrow(new GithubUpstreamException("timeout"));

        assertThatThrownBy(service::getActivity)
                .isInstanceOf(GithubUnavailableException.class)
                .hasMessage("GitHub activity is temporarily unavailable");
    }

    @Test
    void responseDoesNotExposeCacheInternalsOrRawGithubFields() {
        when(githubCacheRepository.findByCacheKey("activity:AmitKumar1404")).thenReturn(Optional.empty());
        when(githubClient.fetchPublicEvents()).thenReturn(List.of(GithubFixtures.pushEvent(), GithubFixtures.deleteEvent()));

        GithubActivityResponse response = service.getActivity();

        assertThat(GithubActivityResponse.class.getRecordComponents())
                .extracting(component -> component.getName())
                .containsExactly("username", "profileUrl", "activities", "source", "cached", "stale");
        assertThat(response.activities())
                .extracting(GithubActivityItemResponse::type)
                .containsExactly(GithubActivityType.PUSH);
        assertThat(response.activities().getFirst().repoUrl()).doesNotContain("api.github.com");
    }
}
