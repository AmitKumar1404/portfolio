package dev.amitkumar.portfolio.project;

public record ProjectResponse(
        String slug,
        String title,
        String shortDescription,
        boolean featured,
        int displayOrder,
        String githubUrl,
        String liveUrl) {

    static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getSlug(),
                project.getTitle(),
                project.getShortDescription(),
                project.isFeatured(),
                project.getSortOrder(),
                project.getGithubUrl(),
                project.getLiveUrl());
    }
}
