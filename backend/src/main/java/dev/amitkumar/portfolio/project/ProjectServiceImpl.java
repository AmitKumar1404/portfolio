package dev.amitkumar.portfolio.project;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.amitkumar.portfolio.common.exception.ResourceNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjects() {
        return projectRepository.findAllByPublishedTrueOrderBySortOrderAsc().stream()
                .map(ProjectResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new ResourceNotFoundException("PROJECT_NOT_FOUND", "Project not found"));
        return ProjectResponse.from(project);
    }
}
