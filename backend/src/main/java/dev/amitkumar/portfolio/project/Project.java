package dev.amitkumar.portfolio.project;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$")
    @Column(nullable = false, length = 120, unique = true)
    private String slug;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @Size(max = 240)
    @Column(length = 240)
    private String subtitle;

    @NotBlank
    @Size(max = 500)
    @Column(name = "short_description", nullable = false, length = 500)
    private String shortDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @Size(max = 80)
    @Column(length = 80)
    private String category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ProjectStatus status;

    @Column(nullable = false)
    private boolean featured;

    @Column(nullable = false)
    private boolean published;

    @Size(max = 500)
    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Size(max = 500)
    @Column(name = "live_url", length = 500)
    private String liveUrl;

    @Size(max = 500)
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Size(max = 200)
    @Column(name = "seo_title", length = 200)
    private String seoTitle;

    @Size(max = 320)
    @Column(name = "seo_description", length = 320)
    private String seoDescription;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    protected Project() {
    }

    Project(
            Long id,
            String slug,
            String title,
            String subtitle,
            String shortDescription,
            String fullDescription,
            String category,
            ProjectStatus status,
            boolean featured,
            boolean published,
            String githubUrl,
            String liveUrl,
            String imageUrl,
            String seoTitle,
            String seoDescription,
            int sortOrder,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.subtitle = subtitle;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.status = status;
        this.featured = featured;
        this.published = published;
        this.githubUrl = githubUrl;
        this.liveUrl = liveUrl;
        this.imageUrl = imageUrl;
        this.seoTitle = seoTitle;
        this.seoDescription = seoDescription;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getCategory() {
        return category;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public boolean isFeatured() {
        return featured;
    }

    public boolean isPublished() {
        return published;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
