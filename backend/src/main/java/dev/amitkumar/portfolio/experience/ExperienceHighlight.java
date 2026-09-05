package dev.amitkumar.portfolio.experience;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "experience_highlights")
public class ExperienceHighlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String body;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    protected ExperienceHighlight() {
    }

    ExperienceHighlight(Long id, String body, int sortOrder, Instant createdAt) {
        this.id = id;
        this.body = body;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
