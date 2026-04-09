# Frontend

React + Vite client for notes management.

## Implemented task requirements

- UI for create/update/delete notes and list view
- State manager: Zustand
- i18n language switch: English/Ukrainian
- 1 e2e test: Playwright

## Prerequisites

- Node.js 20+
- npm

## Run locally

1. Run backend on `http://localhost:8080`
2. From `frontend` directory run:

```bash
npm install
npm run dev
```

Open `http://localhost:5173`.

## Run e2e test

```bash
npx playwright install
npm run e2e
```

## Build for production

```bash
npm run build
npm run preview
```

## Run with Docker Compose

From project root:

```bash
docker compose up --build
```

Frontend is exposed on `http://localhost:3000`.
