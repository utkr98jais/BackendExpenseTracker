# Render Deployment Guide

## 1. Prerequisites

- MongoDB Atlas cluster created (M0 free tier)
- Connection string (SRV URI) copied WITHOUT hardcoding credentials in repo.
- This repository contains a multi-stage `Dockerfile` and uses Java 17.

## 2. Remove Secrets From Repo

Secrets removed from `src/main/resources/application.properties`.
Add environment variable on Render: `SPRING_DATA_MONGODB_URI` with full Atlas connection string including username & password.

## 3. Create Render Service

1. Login to Render.
2. New + > Web Service.
3. Select this GitHub repo.
4. Name: `expense-manager` (any name).
5. Environment: Docker.
6. Region: pick closest to Atlas cluster (usually Oregon / Frankfurt etc.).
7. Branch: `main`.
8. Auto deploy: enable.

## 4. Environment Variables (Render Dashboard > Environment)

Add:

- `SPRING_DATA_MONGODB_URI` = `mongodb+srv://<user>:<password>@<cluster-host>/<db>?retryWrites=true&w=majority&appName=<AppName>`
  - Ensure it includes `databaseName` either via `/ExpenseManager` or using spring.data.mongodb.database property already set.
- (Optional) `JAVA_OPTS` to tune memory.
- (Optional) `JWT_SECRET` to replace hardcoded secret (needs code change later).

## 5. Build & Start Commands

Using Dockerfile, no need to set build/start commands. Render will build with:

- `docker build` using provided `Dockerfile`.
  Exposed port: Render sets `$PORT`. ENTRYPOINT already passes `--server.port=$PORT`.

## 6. Health Check

Define health check path (optional) e.g. `/auth/register` not ideal. Better add a simple endpoint `/health` returning 200. (Future enhancement.) For now leave default.

## 7. Test After Deploy

Once live note Render's provided URL, e.g. `https://expense-manager.onrender.com`.

Test unauthenticated endpoints:

- Register user:
  `POST /auth/register`
- Login user:
  `PUT /auth/login`

Authenticated endpoints (need Bearer token from login):

- `GET /users?username=<username>`

## 8. Common Issues

| Symptom       | Cause               | Fix                                                                                       |
| ------------- | ------------------- | ----------------------------------------------------------------------------------------- |
| Crash OOM     | JVM heap too large  | Lower `JAVA_OPTS` percentages.                                                            |
| 500 on Mongo  | Bad URI or network  | Verify `SPRING_DATA_MONGODB_URI`. Whitelist 0.0.0.0/0 during dev in Atlas Network Access. |
| 403 responses | Missing/invalid JWT | Ensure Authorization header `Bearer <token>`.                                             |
| 404 service   | Build failed        | Check Render build logs.                                                                  |

## 9. Future Hardening

- Move JWT secret to `JWT_SECRET` env var and read it in `JwtUtil`.
- Add `/health` actuator or custom endpoint and optionally Spring Boot Actuator dependency.
- Restrict CORS to your frontend origin.

## 10. Local Docker Run

Build & run locally (replace URI):

```bash
export SPRING_DATA_MONGODB_URI="mongodb+srv://user:pass@cluster0.example.mongodb.net/ExpenseManager?retryWrites=true&w=majority"
export PORT=8080
docker build -t expense-manager .
docker run -p 8080:8080 -e SPRING_DATA_MONGODB_URI -e PORT expense-manager
```

Service available at `http://localhost:8080`.

## 11. Cleanup

If you redeploy, no need to commit secrets; ensure `.gitignore` keeps them out.

---

Deployment guide complete.
