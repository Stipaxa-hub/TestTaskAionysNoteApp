# Notes Management App

Full-stack CRUD application for managing notes.

- **Backend**: Java 21 / Spring Boot 3.4 / PostgreSQL / Liquibase
- **Frontend**: React 18 / Zustand / i18next / Vite

## Requirements covered

- Backend REST endpoints:
  - `GET /notes` — paginated list of notes
  - `GET /notes/{id}` — get note by ID
  - `POST /notes` — create note
  - `PUT /notes/{id}` — update note
  - `DELETE /notes/{id}` — delete note
- PostgreSQL database running in Docker
- Frontend CRUD UI for notes management
- State manager: **Zustand**
- Language switch (i18n): **English / Ukrainian**
- 1 e2e test (Playwright) — full CRUD flow + language switching

## Run with Docker Compose

From project root:

```bash
docker compose up --build
```

Services:

| Service    | URL                      |
|------------|--------------------------|
| PostgreSQL | `localhost:5432`         |
| Backend    | `http://localhost:8080`  |
| Frontend   | `http://localhost:3000`  |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |

> The frontend Nginx container also proxies API calls — you can access
> `http://localhost:3000/notes` and `http://localhost:3000/swagger-ui` directly.

## Run locally (without Docker)

### Prerequisites

- Java 21+
- Node.js 20+
- PostgreSQL running on `localhost:5432` (database: `notesdb`, user/password: `postgres/postgres`)

### Backend

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Backend URL: `http://localhost:8080`

### Frontend

```powershell
cd frontend
copy .env.example .env
npm install
npm run dev
```

Frontend URL: `http://localhost:5173`

## Run backend tests

```powershell
cd backend
.\mvnw.cmd test
```

## Run e2e test

> **Note:** Both the backend and frontend dev server must be running for the e2e test to work.

```powershell
cd frontend
npx playwright install
npm run e2e
```

To run in headed mode (visible browser):

```powershell
npm run e2e:headed
```
