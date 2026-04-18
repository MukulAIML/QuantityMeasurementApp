# measurement-service

Spring Boot microservice that handles all quantity measurement operations and persists history.

**Port:** 8082

## Endpoints  (all require `Authorization: Bearer <token>`)
| Method | Path                              | Description                    |
|--------|-----------------------------------|--------------------------------|
| POST   | /api/v1/quantities/compare        | Compare two quantities         |
| POST   | /api/v1/quantities/convert        | Convert between units          |
| POST   | /api/v1/quantities/add            | Add two quantities             |
| POST   | /api/v1/quantities/subtract       | Subtract two quantities        |
| POST   | /api/v1/quantities/divide         | Divide two quantities          |
| GET    | /api/v1/quantities/history        | Full operation history         |
| GET    | /api/v1/quantities/history/{op}   | History filtered by operation  |
| GET    | /api/v1/quantities/count/{op}     | Count of successful operations |

## Run locally
```bash
mvn spring-boot:run
```

## Deploy (Render / Railway)
Set the following environment variables:
- `JWT_SECRET` – must match auth-service secret
