package dev.amitkumar.portfolio.project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.amitkumar.portfolio.common.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    void getProjectsReturnsMappedDtosInRepositoryOrder() {
        when(projectRepository.findAllByPublishedTrueOrderBySortOrderAsc())
                .thenReturn(List.of(ProjectFixtures.chatbot(), ProjectFixtures.booking(), ProjectFixtures.portfolio()));

        List<ProjectResponse> projects = projectService.getProjects();

        assertThat(projects).extracting(ProjectResponse::slug)
                .containsExactly("chatbot-rag-platform", "local-service-booking", "developer-portfolio");
        assertThat(projects).extracting(ProjectResponse::displayOrder).containsExactly(1, 2, 3);
        assertThat(projects.getFirst().title()).isEqualTo("Chatbot RAG Platform");
        assertThat(projects.getFirst().featured()).isTrue();
        assertThat(projects.get(2).githubUrl()).isEqualTo("https://github.com/AmitKumar1404/portfolio");
    }

    @Test
    void getProjectsReturnsEmptyListWhenNonePublished() {
        when(projectRepository.findAllByPublishedTrueOrderBySortOrderAsc()).thenReturn(List.of());

        assertThat(projectService.getProjects()).isEmpty();
    }

    @Test
    void getProjectBySlugReturnsMappedDto() {
        when(projectRepository.findBySlugAndPublishedTrue("chatbot-rag-platform"))
                .thenReturn(Optional.of(ProjectFixtures.chatbot()));

        ProjectResponse response = projectService.getProjectBySlug("chatbot-rag-platform");

        assertThat(response.slug()).isEqualTo("chatbot-rag-platform");
        assertThat(response.title()).isEqualTo("Chatbot RAG Platform");
        assertThat(response.shortDescription()).isEqualTo("Portfolio RAG chatbot.");
        assertThat(response.displayOrder()).isEqualTo(1);
        assertThat(response.liveUrl()).isNull();
    }

    @Test
    void getProjectBySlugThrowsWhenMissing() {
        when(projectRepository.findBySlugAndPublishedTrue("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getProjectBySlug("missing"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Project not found")
                .extracting(ex -> ((ResourceNotFoundException) ex).getCode())
                .isEqualTo("PROJECT_NOT_FOUND");
    }
}
