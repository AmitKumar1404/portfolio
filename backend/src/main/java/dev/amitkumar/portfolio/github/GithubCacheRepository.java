package dev.amitkumar.portfolio.github;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubCacheRepository extends JpaRepository<GithubCache, Long> {

    Optional<GithubCache> findByCacheKey(String cacheKey);
}
