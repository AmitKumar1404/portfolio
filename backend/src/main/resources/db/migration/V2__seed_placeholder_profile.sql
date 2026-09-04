-- Single placeholder profile for local/dev. Safe to re-run: inserts only when the table is empty.
-- Replace every REPLACE_ME value (and the example email) before publishing the site.
-- github_url uses the known repository owner and can be changed if the public profile differs.

INSERT INTO profiles (
    name,
    headline,
    short_bio,
    long_bio,
    location,
    email,
    github_url,
    linkedin_url,
    website_url,
    resume_url,
    avatar_url,
    availability
)
SELECT
    'Amit Kumar',
    'REPLACE_ME: headline',
    'REPLACE_ME: short bio',
    'REPLACE_ME: long bio',
    'REPLACE_ME: location',
    'replace-me@example.com',
    'https://github.com/AmitKumar1404',
    NULL,
    NULL,
    NULL,
    NULL,
    'SELECTIVE'
WHERE NOT EXISTS (
    SELECT 1 FROM profiles
);
