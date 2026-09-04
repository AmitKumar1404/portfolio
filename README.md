# Amit Kumar — Portfolio

Personal developer portfolio for **Amit Kumar**. The public site is a Vite React application. Content will be served by a Spring Boot API backed by PostgreSQL.

This repository is in **Phase 1: foundation**. The visual portfolio, public APIs, and admin dashboard are not implemented yet.

## Stack

**Frontend:** React, Vite, TypeScript, Tailwind CSS v4, React Router, TanStack Query, Axios, Lucide React, Framer Motion

**Backend:** Java 21, Spring Boot 3.5, Maven, Spring Web, Spring Security, Spring Data JPA, Bean Validation, Flyway, Actuator, Springdoc OpenAPI

**Database:** PostgreSQL 16 (Docker Compose for local development)

## Repository layout

```text
portfolio/
├── docker-compose.yml
├── backend/          Spring Boot API
└── frontend/         Vite + React application
```

## Local development

Requirements:

- Java 21
- Maven 3.9+
- Node.js 20+
- Docker with Compose (for PostgreSQL 16)

### 1. PostgreSQL

```bash
docker compose up -d
```

Compose defaults (override with a root `.env` if needed):

```text
POSTGRES_DB=portfolio
POSTGRES_USER=portfolio
POSTGRES_PASSWORD=portfolio
POSTGRES_PORT=5432
```

The API user is intended for local development only. Do not reuse these values in production.

### 2. Backend

```bash
cd backend
cp .env.example .env   # optional; export the same variables in your shell
mvn spring-boot:run
```

The API listens on `http://localhost:8080`.

Health check:

```bash
curl http://localhost:8080/actuator/health
```

Expected response includes `"status":"UP"` with HTTP 200.

OpenAPI / Swagger UI is enabled on the `local` profile:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

Swagger is disabled in the `prod` profile.

### 3. Frontend

```bash
cd frontend
cp .env.example .env.local   # optional
npm install
npm run dev
```

The app listens on `http://localhost:5173`.

Vite proxies `/api` and `/actuator` to `http://localhost:8080`, so the browser can stay same-origin during development.

Foundation routes:

- `/`
- `/projects/:slug`
- `/resume`
- any unknown path → 404

## Environment variables

Nothing prefixed with `VITE_` is secret. Those values are compiled into the browser bundle.

See:

- `frontend/.env.example`
- `backend/.env.example`

Production must supply datasource credentials, a unique `APP_IP_HASH_PEPPER`, and an explicit `CORS_ALLOWED_ORIGINS` list. Do not use wildcard CORS in production.

## What Phase 1 includes

- Root Compose file for PostgreSQL 16
- Spring Boot application skeleton
- Fail-closed Spring Security baseline
- Configurable CORS
- Global API exception envelope
- Request logging (method, path, status, duration)
- Rate-limit filter extension point (not enforced yet)
- Flyway migration `V1__create_initial_schema.sql`
- Frontend design tokens, theme system (light / dark / system), router, QueryClient, Axios client

## Planned later

- Phase 2: public APIs for profile, projects, skills, experience, and contact
- Phase 3: portfolio sections and visual design
- Phase 4: case studies and interaction (command palette, filtering)
- Phase 5: GitHub activity, resume, contact polish
- Phase 6: SEO, prerender, CI, production deploy
- Phase 7: admin dashboard and authentication

## Tests and builds

```bash
cd backend && mvn test
cd frontend && npm run build
```

## Troubleshooting

- The backend expects Java 21. If `java` is not on your PATH, set `JAVA_HOME` to a JDK 21 installation before running Maven.
- The API defaults to port `8080`. If that port is already taken, start with `SERVER_PORT=8081 mvn spring-boot:run`.
- Docker is the intended way to run PostgreSQL 16. If Compose is unavailable, a local PostgreSQL instance can be used with the same database name, user, and password as `backend/.env.example`.
