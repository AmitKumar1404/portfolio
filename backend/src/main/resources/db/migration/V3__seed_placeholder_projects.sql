-- Placeholder catalog projects for the public API. Inserts only when each slug is missing.
-- Descriptions are portfolio/demo copy, not employment claims.
-- github_url is set only for the known repository; other repos stay null until real URLs exist.

INSERT INTO projects (
    slug,
    title,
    subtitle,
    short_description,
    full_description,
    category,
    status,
    featured,
    published,
    github_url,
    live_url,
    image_url,
    seo_title,
    seo_description,
    sort_order
)
SELECT
    'chatbot-rag-platform',
    'Chatbot RAG Platform',
    'Retrieval-augmented chatbot',
    'Portfolio project: RAG chatbot with document retrieval, streaming answers, and source citations.',
    NULL,
    'AI',
    'WIP',
    TRUE,
    TRUE,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    1
WHERE NOT EXISTS (
    SELECT 1 FROM projects WHERE slug = 'chatbot-rag-platform'
);

INSERT INTO projects (
    slug,
    title,
    subtitle,
    short_description,
    full_description,
    category,
    status,
    featured,
    published,
    github_url,
    live_url,
    image_url,
    seo_title,
    seo_description,
    sort_order
)
SELECT
    'local-service-booking',
    'Local Service Booking',
    'Booking flow for local services',
    'Portfolio project: service booking with availability, reservations, and a customer-facing flow.',
    NULL,
    'Full-stack',
    'WIP',
    FALSE,
    TRUE,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    2
WHERE NOT EXISTS (
    SELECT 1 FROM projects WHERE slug = 'local-service-booking'
);

INSERT INTO projects (
    slug,
    title,
    subtitle,
    short_description,
    full_description,
    category,
    status,
    featured,
    published,
    github_url,
    live_url,
    image_url,
    seo_title,
    seo_description,
    sort_order
)
SELECT
    'developer-portfolio',
    'Developer Portfolio',
    'This site',
    'Public API-backed personal portfolio for projects, skills, experience, and contact.',
    NULL,
    'Full-stack',
    'WIP',
    FALSE,
    TRUE,
    'https://github.com/AmitKumar1404/portfolio',
    NULL,
    NULL,
    NULL,
    NULL,
    3
WHERE NOT EXISTS (
    SELECT 1 FROM projects WHERE slug = 'developer-portfolio'
);
