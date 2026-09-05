-- Placeholder work history for the public API. Not real employment.
-- Inserts only when the matching placeholder row is missing.

INSERT INTO experiences (
    company,
    role,
    employment_type,
    location,
    company_url,
    start_date,
    end_date,
    is_current,
    description,
    sort_order
)
SELECT
    'REPLACE_ME: Company',
    'REPLACE_ME: Role',
    'FULL_TIME',
    'REPLACE_ME: Location',
    NULL,
    DATE '2024-01-01',
    NULL,
    TRUE,
    'REPLACE_ME: Experience description',
    1
WHERE NOT EXISTS (
    SELECT 1
    FROM experiences
    WHERE company = 'REPLACE_ME: Company'
      AND sort_order = 1
);

INSERT INTO experiences (
    company,
    role,
    employment_type,
    location,
    company_url,
    start_date,
    end_date,
    is_current,
    description,
    sort_order
)
SELECT
    'REPLACE_ME: Previous Company',
    'REPLACE_ME: Previous Role',
    'CONTRACT',
    'REPLACE_ME: Location',
    NULL,
    DATE '2022-01-01',
    DATE '2023-12-31',
    FALSE,
    'REPLACE_ME: Previous experience description',
    2
WHERE NOT EXISTS (
    SELECT 1
    FROM experiences
    WHERE company = 'REPLACE_ME: Previous Company'
      AND sort_order = 2
);

INSERT INTO experience_highlights (experience_id, body, sort_order)
SELECT e.id, 'REPLACE_ME: Achievement or responsibility', 1
FROM experiences e
WHERE e.company = 'REPLACE_ME: Company'
  AND e.sort_order = 1
  AND NOT EXISTS (
      SELECT 1
      FROM experience_highlights h
      WHERE h.experience_id = e.id
        AND h.sort_order = 1
  );

INSERT INTO experience_highlights (experience_id, body, sort_order)
SELECT e.id, 'REPLACE_ME: Achievement or responsibility', 2
FROM experiences e
WHERE e.company = 'REPLACE_ME: Company'
  AND e.sort_order = 1
  AND NOT EXISTS (
      SELECT 1
      FROM experience_highlights h
      WHERE h.experience_id = e.id
        AND h.sort_order = 2
  );

INSERT INTO experience_highlights (experience_id, body, sort_order)
SELECT e.id, 'REPLACE_ME: Achievement or responsibility', 1
FROM experiences e
WHERE e.company = 'REPLACE_ME: Previous Company'
  AND e.sort_order = 2
  AND NOT EXISTS (
      SELECT 1
      FROM experience_highlights h
      WHERE h.experience_id = e.id
        AND h.sort_order = 1
  );
