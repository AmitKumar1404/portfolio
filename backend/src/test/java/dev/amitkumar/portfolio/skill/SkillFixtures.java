package dev.amitkumar.portfolio.skill;

import java.time.Instant;

final class SkillFixtures {

    private SkillFixtures() {
    }

    static SkillCategory backend() {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new SkillCategory(1L, "REPLACE_ME: Backend", "backend", 1, now, now);
    }

    static SkillCategory frontend() {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new SkillCategory(2L, "REPLACE_ME: Frontend", "frontend", 2, now, now);
    }

    static Skill java() {
        return skill(10L, backend(), "REPLACE_ME: Java", 1);
    }

    static Skill springBoot() {
        return skill(11L, backend(), "REPLACE_ME: Spring Boot", 2);
    }

    static Skill react() {
        return skill(20L, frontend(), "REPLACE_ME: React", 1);
    }

    private static Skill skill(Long id, SkillCategory category, String name, int sortOrder) {
        Instant now = Instant.parse("2026-09-05T12:00:00Z");
        return new Skill(id, category, name, SkillLevel.WORKING, null, false, sortOrder, now, now);
    }
}
