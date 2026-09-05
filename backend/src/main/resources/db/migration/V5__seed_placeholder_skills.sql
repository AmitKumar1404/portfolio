-- Placeholder skill taxonomy for the public API. Not real proficiency claims.
-- Inserts only when the matching category slug or skill name is missing.

INSERT INTO skill_categories (name, slug, sort_order)
SELECT 'REPLACE_ME: Backend', 'backend', 1
WHERE NOT EXISTS (SELECT 1 FROM skill_categories WHERE slug = 'backend');

INSERT INTO skill_categories (name, slug, sort_order)
SELECT 'REPLACE_ME: Frontend', 'frontend', 2
WHERE NOT EXISTS (SELECT 1 FROM skill_categories WHERE slug = 'frontend');

INSERT INTO skill_categories (name, slug, sort_order)
SELECT 'REPLACE_ME: Database', 'database', 3
WHERE NOT EXISTS (SELECT 1 FROM skill_categories WHERE slug = 'database');

INSERT INTO skill_categories (name, slug, sort_order)
SELECT 'REPLACE_ME: Tools', 'tools', 4
WHERE NOT EXISTS (SELECT 1 FROM skill_categories WHERE slug = 'tools');

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Java', 'WORKING', NULL, FALSE, 1
FROM skill_categories c
WHERE c.slug = 'backend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Java'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Spring Boot', 'WORKING', NULL, FALSE, 2
FROM skill_categories c
WHERE c.slug = 'backend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Spring Boot'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Spring Data JPA', 'WORKING', NULL, FALSE, 3
FROM skill_categories c
WHERE c.slug = 'backend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Spring Data JPA'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: REST APIs', 'WORKING', NULL, FALSE, 4
FROM skill_categories c
WHERE c.slug = 'backend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: REST APIs'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: React', 'WORKING', NULL, FALSE, 1
FROM skill_categories c
WHERE c.slug = 'frontend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: React'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: TypeScript', 'WORKING', NULL, FALSE, 2
FROM skill_categories c
WHERE c.slug = 'frontend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: TypeScript'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Vite', 'WORKING', NULL, FALSE, 3
FROM skill_categories c
WHERE c.slug = 'frontend'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Vite'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: PostgreSQL', 'WORKING', NULL, FALSE, 1
FROM skill_categories c
WHERE c.slug = 'database'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: PostgreSQL'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Flyway', 'WORKING', NULL, FALSE, 2
FROM skill_categories c
WHERE c.slug = 'database'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Flyway'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Git', 'WORKING', NULL, FALSE, 1
FROM skill_categories c
WHERE c.slug = 'tools'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Git'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: GitHub', 'WORKING', NULL, FALSE, 2
FROM skill_categories c
WHERE c.slug = 'tools'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: GitHub'
  );

INSERT INTO skills (category_id, name, level, years, featured, sort_order)
SELECT c.id, 'REPLACE_ME: Docker', 'WORKING', NULL, FALSE, 3
FROM skill_categories c
WHERE c.slug = 'tools'
  AND NOT EXISTS (
      SELECT 1 FROM skills s WHERE s.category_id = c.id AND s.name = 'REPLACE_ME: Docker'
  );
