package dev.amitkumar.portfolio.project;

import java.util.List;

public interface ProjectService {

    List<ProjectResponse> getProjects();

    ProjectResponse getProjectBySlug(String slug);
}
