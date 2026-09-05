package dev.amitkumar.portfolio.github;

import java.time.Instant;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "github_cache")
public class GithubCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 80)
    @Column(name = "cache_key", nullable = false, length = 80)
    private String cacheKey;

    @NotNull
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private GithubActivitySnapshot payload;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    protected GithubCache() {
    }

    GithubCache(
            Long id,
            String cacheKey,
            GithubActivitySnapshot payload,
            Instant expiresAt,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.cacheKey = cacheKey;
        this.payload = payload;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    void replace(GithubActivitySnapshot payload, Instant expiresAt) {
        this.payload = payload;
        this.expiresAt = expiresAt;
    }

    boolean isFresh(Instant now) {
        return expiresAt != null && expiresAt.isAfter(now);
    }

    public Long getId() {
        return id;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public GithubActivitySnapshot getPayload() {
        return payload;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
