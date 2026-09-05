package dev.amitkumar.portfolio.skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("""
            SELECT s FROM Skill s
            JOIN FETCH s.category
            ORDER BY s.category.sortOrder ASC, s.category.id ASC, s.sortOrder ASC, s.id ASC
            """)
    List<Skill> findAllPublicOrdered();
}
