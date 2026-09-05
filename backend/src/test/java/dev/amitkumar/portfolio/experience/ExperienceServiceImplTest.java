package dev.amitkumar.portfolio.experience;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceImplTest {

    @Mock
    private ExperienceRepository experienceRepository;

    private ExperienceServiceImpl experienceService;

    @BeforeEach
    void setUp() {
        experienceService = new ExperienceServiceImpl(experienceRepository);
    }

    @Test
    void getExperiencesReturnsMappedDtosInRepositoryOrder() {
        when(experienceRepository.findAllByOrderBySortOrderAscIdAsc())
                .thenReturn(List.of(ExperienceFixtures.currentRole(), ExperienceFixtures.previousRole()));

        List<ExperienceResponse> experiences = experienceService.getExperiences();

        assertThat(experiences).extracting(ExperienceResponse::company)
                .containsExactly("REPLACE_ME: Company", "REPLACE_ME: Previous Company");
        assertThat(experiences).extracting(ExperienceResponse::displayOrder).containsExactly(1, 2);
        assertThat(experiences.getFirst().role()).isEqualTo("REPLACE_ME: Role");
        assertThat(experiences.getFirst().current()).isTrue();
        assertThat(experiences.getFirst().endDate()).isNull();
        assertThat(experiences.getFirst().highlights()).hasSize(2);
        assertThat(experiences.get(1).employmentType()).isEqualTo(EmploymentType.CONTRACT);
        assertThat(experiences.get(1).endDate()).isEqualTo(LocalDate.of(2023, 12, 31));
    }

    @Test
    void getExperiencesReturnsEmptyListWhenNoneExist() {
        when(experienceRepository.findAllByOrderBySortOrderAscIdAsc()).thenReturn(List.of());

        assertThat(experienceService.getExperiences()).isEmpty();
    }
}
