# Quantity Measurement App — Microservices

Four independent services that together implement the full Quantity Measurement application.

```
┌─────────────────────────────────────────────┐
│              cli-service                     │
│        (Spring Shell interactive CLI)        │
└──────────────────┬──────────────────────────┘
                   │ HTTP
┌──────────────────▼──────────────────────────┐
│              api-gateway  :8080              │
│   JWT validation + reverse proxy            │
└────────────┬─────────────────┬──────────────┘
             │                 │
┌────────────▼──────┐ ┌────────▼──────────────┐
│  auth-service     │ │  measurement-service   │
│     :8081         │ │       :8082            │
│  Register / Login │ │  Compare/Convert/Add   │
│  JWT issuance     │ │  Subtract/Divide       │
│  H2 (users)       │ │  History  |  H2        │
└───────────────────┘ └────────────────────────┘
```

## Services

| Service              | Port | Tech                       | Responsibility                  |
|----------------------|------|----------------------------|---------------------------------|
| auth-service         | 8081 | Spring Boot + JWT          | User register/login, JWT issue  |
| measurement-service  | 8082 | Spring Boot + JPA          | Quantity ops + history          |
| api-gateway          | 8080 | Spring Boot                | JWT guard + reverse proxy       |
| cli-service          | —    | Spring Boot + Spring Shell | Interactive CLI client          |

## Quick Start (local)

```bash
# Terminal 1
cd auth-service && mvn spring-boot:run

# Terminal 2
cd measurement-service && mvn spring-boot:run

# Terminal 3
cd api-gateway && mvn spring-boot:run

# Terminal 4 — interactive shell opens automatically
cd cli-service && mvn spring-boot:run
```

Inside the shell:

```
shell:> register --username alice --password secret
shell:> login --username alice --password secret
shell:> compare --v1 100 --u1 CENTIMETER --t1 LENGTH --v2 1 --u2 METER --t2 LENGTH
shell:> history
shell:> exit
```

## Cloud Deployment (2 services per platform)

### Platform A  (e.g. Render)
- auth-service
- measurement-service

### Platform B  (e.g. Railway)
- api-gateway  (set `AUTH_SERVICE_URL` and `MEASUREMENT_SERVICE_URL` to Platform A URLs)
- cli-service  (set `API_GATEWAY_URL` to the api-gateway URL on Platform B)

## Environment Variables

| Variable                | Used by             | Description                        |
|-------------------------|---------------------|------------------------------------|
| JWT_SECRET              | all backend services| Shared hex-encoded 256-bit secret  |
| JWT_EXPIRATION          | auth-service        | Token TTL in ms (default 86400000) |
| AUTH_SERVICE_URL        | api-gateway         | Base URL of auth-service           |
| MEASUREMENT_SERVICE_URL | api-gateway         | Base URL of measurement-service    |
| API_GATEWAY_URL         | cli-service         | Base URL of api-gateway            |
