package dev.amitkumar.portfolio.experience;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 160)
    @Column(nullable = false, length = 160)
    private String company;

    @NotBlank
    @Size(max = 160)
    @Column(nullable = false, length = 160)
    private String role;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false, length = 32)
    private EmploymentType employmentType;

    @Size(max = 160)
    @Column(length = 160)
    private String location;

    @Size(max = 500)
    @Column(name = "company_url", length = 500)
    private String companyUrl;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current", nullable = false)
    private boolean current;

    @Column
    private String description;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id", insertable = false, updatable = false)
    @OrderBy("sortOrder ASC")
    private List<ExperienceHighlight> highlights = new ArrayList<>();

    protected Experience() {
    }

    Experience(
            Long id,
            String company,
            String role,
            EmploymentType employmentType,
            String location,
            String companyUrl,
            LocalDate startDate,
            LocalDate endDate,
            boolean current,
            String description,
            int sortOrder,
            Instant createdAt,
            Instant updatedAt,
            List<ExperienceHighlight> highlights) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.employmentType = employmentType;
        this.location = location;
        this.companyUrl = companyUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.current = current;
        this.description = description;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.highlights = highlights == null ? new ArrayList<>() : List.copyOf(highlights);
    }

    public Long getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getRole() {
        return role;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public String getLocation() {
        return location;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isCurrent() {
        return current;
    }

    public String getDescription() {
        return description;
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

    public List<ExperienceHighlight> getHighlights() {
        return List.copyOf(highlights);
    }
}
