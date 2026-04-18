# auth-service

Spring Boot microservice responsible for user registration, login, and JWT issuance.

**Port:** 8081

## Endpoints
| Method | Path            | Auth required | Description            |
|--------|-----------------|---------------|------------------------|
| POST   | /auth/register  | No            | Register new user      |
| POST   | /auth/login     | No            | Login, receive JWT     |
| POST   | /auth/validate  | No            | Validate JWT (internal)|

## Run locally
```bash
mvn spring-boot:run
```

## Deploy (Render / Railway)
Set the following environment variables:
- `JWT_SECRET` – hex-encoded 256-bit secret (default provided for dev)
- `JWT_EXPIRATION` – token TTL in ms (default 86400000)
