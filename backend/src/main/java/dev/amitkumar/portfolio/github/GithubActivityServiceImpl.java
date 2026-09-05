package dev.amitkumar.portfolio.github;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GithubActivityServiceImpl implements GithubActivityService {

    private static final Logger log = LoggerFactory.getLogger(GithubActivityServiceImpl.class);

    private final GithubClient githubClient;
    private final GithubCacheRepository githubCacheRepository;
    private final GithubProperties properties;
    private final GithubActivityMapper mapper;

    public GithubActivityServiceImpl(
            GithubClient githubClient,
            GithubCacheRepository githubCacheRepository,
            GithubProperties properties) {
        this.githubClient = githubClient;
        this.githubCacheRepository = githubCacheRepository;
        this.properties = properties;
        this.mapper = new GithubActivityMapper(properties);
    }

    @Override
    @Transactional
    public GithubActivityResponse getActivity() {
        String cacheKey = "activity:" + properties.username();
        Optional<GithubCache> cached = githubCacheRepository.findByCacheKey(cacheKey);
        Instant now = Instant.now();

        if (cached.isPresent() && cached.get().getPayload() != null && cached.get().isFresh(now)) {
            return GithubActivityResponse.from(cached.get().getPayload(), true, false);
        }

        try {
            GithubActivitySnapshot snapshot = mapper.toSnapshot(githubClient.fetchPublicEvents());
            persist(cacheKey, cached.orElse(null), snapshot, now.plus(properties.cacheTtl()));
            return GithubActivityResponse.from(snapshot, false, false);
        } catch (GithubUpstreamException ex) {
            if (cached.isPresent() && cached.get().getPayload() != null) {
                log.warn("Returning stale GitHub activity cache after upstream failure");
                return GithubActivityResponse.from(cached.get().getPayload(), true, true);
            }
            throw new GithubUnavailableException();
        }
    }

    private void persist(String cacheKey, GithubCache existing, GithubActivitySnapshot snapshot, Instant expiresAt) {
        if (existing == null) {
            githubCacheRepository.save(new GithubCache(null, cacheKey, snapshot, expiresAt, null, null));
            return;
        }
        existing.replace(snapshot, expiresAt);
        githubCacheRepository.save(existing);
    }
}
