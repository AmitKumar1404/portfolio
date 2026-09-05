package dev.amitkumar.portfolio.experience;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findAllByOrderBySortOrderAscIdAsc();
}
