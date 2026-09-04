package dev.amitkumar.portfolio.profile;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String name;

    @Size(max = 240)
    @Column(length = 240)
    private String headline;

    @Size(max = 500)
    @Column(name = "short_bio", length = 500)
    private String shortBio;

    @Column(name = "long_bio")
    private String longBio;

    @Size(max = 160)
    @Column(length = 160)
    private String location;

    @Email
    @Size(max = 254)
    @Column(length = 254)
    private String email;

    @Size(max = 500)
    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Size(max = 500)
    @Column(name = "linkedin_url", length = 500)
    private String linkedinUrl;

    @Size(max = 500)
    @Column(name = "website_url", length = 500)
    private String websiteUrl;

    @Size(max = 500)
    @Column(name = "resume_url", length = 500)
    private String resumeUrl;

    @Size(max = 500)
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Availability availability;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    protected Profile() {
    }

    Profile(
            Long id,
            String name,
            String headline,
            String shortBio,
            String longBio,
            String location,
            String email,
            String githubUrl,
            String linkedinUrl,
            String websiteUrl,
            String resumeUrl,
            String avatarUrl,
            Availability availability,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.headline = headline;
        this.shortBio = shortBio;
        this.longBio = longBio;
        this.location = location;
        this.email = email;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.websiteUrl = websiteUrl;
        this.resumeUrl = resumeUrl;
        this.avatarUrl = avatarUrl;
        this.availability = availability;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeadline() {
        return headline;
    }

    public String getShortBio() {
        return shortBio;
    }

    public String getLongBio() {
        return longBio;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Availability getAvailability() {
        return availability;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
