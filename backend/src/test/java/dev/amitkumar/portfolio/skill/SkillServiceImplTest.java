package dev.amitkumar.portfolio.skill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    private SkillServiceImpl skillService;

    @BeforeEach
    void setUp() {
        skillService = new SkillServiceImpl(skillRepository);
    }

    @Test
    void getSkillsGroupsAndPreservesOrder() {
        when(skillRepository.findAllPublicOrdered())
                .thenReturn(List.of(SkillFixtures.java(), SkillFixtures.springBoot(), SkillFixtures.react()));

        List<SkillResponse> skills = skillService.getSkills();

        assertThat(skills).extracting(SkillResponse::slug).containsExactly("backend", "frontend");
        assertThat(skills).extracting(SkillResponse::displayOrder).containsExactly(1, 2);
        assertThat(skills.getFirst().category()).isEqualTo("REPLACE_ME: Backend");
        assertThat(skills.getFirst().skills()).containsExactly("REPLACE_ME: Java", "REPLACE_ME: Spring Boot");
        assertThat(skills.get(1).skills()).containsExactly("REPLACE_ME: React");
    }

    @Test
    void getSkillsReturnsEmptyListWhenNoneExist() {
        when(skillRepository.findAllPublicOrdered()).thenReturn(List.of());

        assertThat(skillService.getSkills()).isEmpty();
    }
}
