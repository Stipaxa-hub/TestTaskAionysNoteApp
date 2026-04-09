# Backend

Spring Boot REST API for notes CRUD with PostgreSQL and Liquibase.

## Run locally

1. Start PostgreSQL (or use Docker Compose from project root).
2. From `backend` directory run:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

API default URL: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Run with Docker Compose

From project root:

```bash
docker compose up --build
```

This starts:
- PostgreSQL on `localhost:5432`
- Backend on `localhost:8080`
- Frontend on `localhost:3000`

## Environment variables

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SERVER_PORT`

Copy `.env.example` from project root and adjust values if needed.
