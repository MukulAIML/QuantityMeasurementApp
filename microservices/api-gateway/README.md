# api-gateway

Spring Boot API Gateway — single entry point for the frontend. Validates JWTs and proxies requests to auth-service and measurement-service.

**Port:** 8080

## Routing
| Prefix              | Forwarded to        |
|---------------------|---------------------|
| /auth/**            | auth-service:8081   |
| /api/v1/quantities/**| measurement-service:8082 |

## Run locally
```bash
mvn spring-boot:run
```

## Deploy (Render / Railway)
Set the following environment variables:
- `AUTH_SERVICE_URL`        – URL of deployed auth-service
- `MEASUREMENT_SERVICE_URL` – URL of deployed measurement-service
- `JWT_SECRET`              – must match the other services

Override properties with env vars using Spring Boot convention, e.g.:
```
AUTH_SERVICE_URL=https://auth-service.onrender.com
MEASUREMENT_SERVICE_URL=https://measurement-service.onrender.com
```
